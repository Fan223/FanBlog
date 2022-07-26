package fan.fanblog.blog.service;

import fan.fanblog.blog.vo.BlogVO;

import java.util.List;

public interface BlogService {
    List<BlogVO> queryAllBlog();

    int addBlog(BlogVO blogVO);
}
