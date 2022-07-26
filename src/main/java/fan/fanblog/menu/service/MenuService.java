package fan.fanblog.menu.service;

import fan.fanblog.menu.vo.MenuVO;

import java.util.List;

public interface MenuService {
    List<MenuVO> queryAllMenu();

    int addMenu(MenuVO blogVO);
}
