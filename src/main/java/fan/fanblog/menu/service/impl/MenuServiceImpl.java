package fan.fanblog.menu.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fan.fanblog.menu.dao.MenuDAO;
import fan.fanblog.menu.entity.MenuDO;
import fan.fanblog.menu.service.MenuService;
import fan.fanblog.menu.vo.MenuVO;
import fan.fanblog.utils.MapStruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @Override
    public List<MenuVO> queryAllMenu() {
        List<MenuDO> blogDOS = menuDAO.selectList(new QueryWrapper<>());

        List<MenuVO> menuVOS = blogDOS.stream().map(menuDO -> MapStruct.INSTANCE.MenuDOToMenuVO(menuDO))
                .collect(Collectors.toList());

        return buildMenuTree(menuVOS);
    }

    private List<MenuVO> buildMenuTree(List<MenuVO> menuVOS) {
        List<MenuVO> menuTree = new ArrayList<>(menuVOS.size());

        for (MenuVO parent : menuVOS) {
            for (MenuVO child : menuVOS) {
                if (child.getParentId().equals(parent.getMenuId())) {
                    parent.getChildren().add(child);
                }
            }

            if (parent.getParentId().equals("0")) {
                menuTree.add(parent);
            }
        }
        return menuTree;
    }

    @Override
    public int addMenu(MenuVO menuVO) {
        MenuDO menuDO = MapStruct.INSTANCE.MenuVOToMenuDO(menuVO);

        menuDO.setMenuId(UUID.randomUUID().toString());
        menuDO.setValiFlag(1);
        menuDO.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        menuDO.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));

        if (StringUtils.isBlank(menuDO.getParentId())) {
            menuDO.setParentId("0");
        }

        return menuDAO.insert(menuDO);
    }


}
