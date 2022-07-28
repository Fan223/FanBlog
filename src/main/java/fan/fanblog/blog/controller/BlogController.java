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

    @PostMapping("/addBlog")
    public BlogResult add(@RequestBody BlogVO blogVO) {
        return BlogResult.success(blogService.addBlog(blogVO));
    }
}
