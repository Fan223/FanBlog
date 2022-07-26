package fan.fanblog.menu.controller;

import fan.fanblog.menu.service.MenuService;
import fan.fanblog.menu.vo.MenuVO;
import fan.fanblog.utils.BlogResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    public BlogResult addMenu(@RequestBody MenuVO menuVO) {
        return BlogResult.success(menuService.addMenu(menuVO));
    }

    @PutMapping("/updateMenu")
    public BlogResult updateMenu(@RequestBody MenuVO menuVO) {
        return BlogResult.success(menuService.updateMenu(menuVO));
    }

    @DeleteMapping("/deleteMenu")
    public BlogResult deleteMenu(@RequestBody ArrayList<String> idList) {
        return BlogResult.success(menuService.deleteMenu(idList));
    }

    @GetMapping("/getCategory")
    public BlogResult getCategory() {
        return BlogResult.success(menuService.getCategory());
    }
}
