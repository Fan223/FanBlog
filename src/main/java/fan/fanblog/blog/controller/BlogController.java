package fan.fanblog.blog.controller;

import fan.fanblog.blog.service.BlogService;
import fan.fanblog.utils.BlogResult;
import fan.fanblog.blog.vo.BlogVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BlogController {

    @Resource
    private BlogService blogService;

    @GetMapping("/blog/queryAllBlog")
    public BlogResult queryAllBlog() {
        return BlogResult.success(blogService.queryAllBlog());
    }

    @PostMapping("/blog/addBlog")
    public BlogResult add(@RequestBody BlogVO blogVO) {
        return BlogResult.success(blogService.addBlog(blogVO));
    }
}
