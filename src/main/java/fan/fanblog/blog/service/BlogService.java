package fan.fanblog.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import fan.fanblog.blog.dto.BlogDTO;
import fan.fanblog.blog.entity.BlogDO;
import fan.fanblog.blog.vo.BlogVO;

import java.util.ArrayList;
import java.util.List;

public interface BlogService extends IService<BlogDO> {
    Page<BlogVO> queryBlog(BlogDTO blogDTO);

    int addBlog(BlogVO blogVO);

    BlogVO queryBlogByMenuId(String menuId);

    BlogVO saveBlog(BlogVO blogVO);

    int deleteBlog(ArrayList<String> idList);
}
