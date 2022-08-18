package fan.fanblog.menu.service;

import fan.fanblog.menu.vo.MenuVO;

import java.util.ArrayList;
import java.util.List;

public interface MenuService {
    List<MenuVO> queryAllMenu();

    int addMenu(MenuVO blogVO);

    int updateMenu(MenuVO menuVO);

    int deleteMenu(ArrayList<String> idList);

    List<MenuVO> getCategory();

    List<String> getMenuIdByParentId(String parentId);

    void updateParentId(String blogId, String parentId);
}
