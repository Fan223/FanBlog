package fan.fanblog.service;

import fan.fanblog.vo.BlogVO;

import java.util.List;

public interface BlogService {
    List<BlogVO> queryAllBlog();

    int addBlog(BlogVO blogVO);
}
