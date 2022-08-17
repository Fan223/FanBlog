package fan.fanblog.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import fan.fanblog.blog.entity.BlogDO;
import fan.fanblog.blog.vo.BlogVO;

import java.util.List;

public interface BlogService extends IService<BlogDO> {
    Page<BlogVO> queryAllBlog();

    int addBlog(BlogVO blogVO);

    BlogVO queryBlogByMenuId(String menuId);

    BlogVO saveBlog(BlogVO blogVO);

    int deleteBlog(BlogVO blogVO);
}
