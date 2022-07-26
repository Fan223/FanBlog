package fan.fanblog.menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fan.fanblog.menu.dao.MenuDAO;
import fan.fanblog.menu.entity.MenuDO;
import fan.fanblog.menu.service.MenuService;
import fan.fanblog.menu.vo.MenuVO;
import fan.fanblog.utils.MapStruct;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuDAO menuDAO;

    @Override
    public List<MenuVO> queryAllMenu() {
        List<MenuDO> blogDOS = menuDAO.selectList(new QueryWrapper<>());

        List<MenuVO> menuVOS = blogDOS.stream().map(menuDO -> MapStruct.INSTANCE.MenuDOToMenuVO(menuDO))
                .collect(Collectors.toList());

        return menuVOS;
    }

    @Override
    public int addMenu(MenuVO menuVO) {

        return menuDAO.insert(MapStruct.INSTANCE.MenuVOToMenuDO(menuVO));
    }


}
