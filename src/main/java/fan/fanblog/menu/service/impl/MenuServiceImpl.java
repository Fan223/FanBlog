package fan.fanblog.menu.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fan.fanblog.blog.dao.BlogDAO;
import fan.fanblog.blog.entity.BlogDO;
import fan.fanblog.blog.service.BlogService;
import fan.fanblog.menu.dao.MenuDAO;
import fan.fanblog.menu.entity.MenuDO;
import fan.fanblog.menu.service.MenuService;
import fan.fanblog.menu.vo.MenuVO;
import fan.fanblog.utils.MapStruct;
import fan.fanblog.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuDAO menuDAO;

    @Resource
    private BlogDAO blogDAO;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public List<MenuVO> queryAllMenu() {
        List<MenuDO> menuDOS = menuDAO.selectList(new QueryWrapper<MenuDO>().orderByAsc("order_num"));

        List<MenuVO> menuVOS = menuDOS.stream().map(menuDO -> MapStruct.INSTANCE.MenuDOToMenuVO(menuDO))
                .collect(Collectors.toList());
        redisUtil.set("menuList", menuVOS.stream().map(menuVO -> menuVO.getMenuId()).collect(Collectors.toList()));

        return buildMenuTree(menuVOS);
    }

    private List<MenuVO> buildMenuTree(List<MenuVO> menuVOS) {
        List<MenuVO> menuTree = new ArrayList<>(menuVOS.size());

        for (MenuVO parent : menuVOS) {
            for (MenuVO child : menuVOS) {
                if (child.getParentId().equals(parent.getMenuId())) {
                    child.setParentName(parent.getMenuName());
                    parent.getChildren().add(child);
                }
            }

            if (parent.getParentId().equals("0")) {
                menuTree.add(parent);
            }
        }
        return menuTree;
    }

    @Transactional
    @Override
    public int addMenu(MenuVO menuVO) {
        MenuDO menuDO = MapStruct.INSTANCE.MenuVOToMenuDO(menuVO);

        if (StringUtils.isBlank(menuDO.getMenuId())) {
            menuDO.setMenuId(UUID.randomUUID().toString());
        }
        menuDO.setValiFlag(1);
        menuDO.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        menuDO.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));

        if (StringUtils.isBlank(menuDO.getParentId())) {
            menuDO.setParentId("0");
        }

        return menuDAO.insert(menuDO);
    }

    @Transactional
    @Override
    public int updateMenu(MenuVO menuVO) {
        MenuDO menuDO = MapStruct.INSTANCE.MenuVOToMenuDO(menuVO);
        if (StringUtils.isBlank(menuVO.getParentId())) {
            menuDO.setParentId("0");
        }
        menuDO.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));

        return menuDAO.updateById(menuDO);
    }

    @Override
    public int deleteMenu(ArrayList<String> idList) {
        blogDAO.deleteBatchIds(idList);
        return menuDAO.deleteBatchIds(idList);
    }

    @Override
    public List<MenuVO> getCategory() {
        List<MenuDO> menuDOS = menuDAO.selectList(new QueryWrapper<MenuDO>().eq("type", 1).eq("permission", ""));
        return MapStruct.INSTANCE.MenuDOListToMenuVOList(menuDOS);
    }

    @Override
    public List<String> getMenuIdByParentId(String parentId) {

        return menuDAO.selectList(new QueryWrapper<MenuDO>().eq("parent_id", parentId)).stream()
                .map(menuDO -> menuDO.getMenuId()).collect(Collectors.toList());
    }

    @Override
    public void updateParentId(String menuId, String parentId) {
        menuDAO.updateParentId(menuId, parentId);
    }
}
