package fan.fanblog.menu.controller;

import fan.fanblog.menu.service.MenuService;
import fan.fanblog.menu.vo.MenuVO;
import fan.fanblog.utils.BlogResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping("/blog/queryAllMenu")
    public BlogResult queryAllBlog() {
        return BlogResult.success(menuService.queryAllMenu());
    }

    @PostMapping("/blog/addMenu")
    public BlogResult add(@RequestBody MenuVO menuVO) {
        return BlogResult.success(menuService.addMenu(menuVO));
    }
}
