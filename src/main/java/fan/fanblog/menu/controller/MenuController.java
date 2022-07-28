package fan.fanblog.menu.controller;

import fan.fanblog.menu.service.MenuService;
import fan.fanblog.menu.vo.MenuVO;
import fan.fanblog.utils.BlogResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping("/queryAllMenu")
    public BlogResult queryAllBlog() {
        return BlogResult.success(menuService.queryAllMenu());
    }

    @PostMapping("/addMenu")
    public BlogResult add(@RequestBody MenuVO menuVO) {
        return BlogResult.success(menuService.addMenu(menuVO));
    }
}
