package fan.fanblog.blog.controller;

import fan.fanblog.blog.service.BlogService;
import fan.fanblog.utils.BlogResult;
import fan.fanblog.blog.vo.BlogVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private BlogService blogService;

    @GetMapping("/queryAllBlog")
    public BlogResult queryAllBlog() {
        return BlogResult.success(blogService.queryAllBlog());
    }

    @GetMapping("/queryBlogByMenuId")
    public BlogResult queryBlogByMenuId(@RequestParam("menuId") String menuId) {
        return BlogResult.success(blogService.queryBlogByMenuId(menuId));
    }

    @PostMapping("/addBlog")
    public BlogResult addBlog(@RequestBody BlogVO blogVO) {
        return BlogResult.success(blogService.addBlog(blogVO));
    }

    @PostMapping("/saveBlog")
    public BlogResult saveBlog(@RequestBody BlogVO blogVO) {
        return BlogResult.success("保存成功", blogService.saveBlog(blogVO));
    }

    @DeleteMapping("/deleteBlog")
    public BlogResult deleteBlog(@RequestBody BlogVO blogVO) {
        return BlogResult.success("删除成功", blogService.deleteBlog(blogVO));
    }
}
