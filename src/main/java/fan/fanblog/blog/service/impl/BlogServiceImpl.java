package fan.fanblog.blog.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fan.fanblog.blog.dao.BlogDAO;
import fan.fanblog.blog.entity.BlogDO;
import fan.fanblog.blog.service.BlogService;
import fan.fanblog.utils.MapStruct;
import fan.fanblog.blog.vo.BlogVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogDAO, BlogDO> implements BlogService {

    @Resource
    private BlogDAO blogDAO;

    @Override
    public List<BlogVO> queryAllBlog() {
        List<BlogDO> blogDOS = blogDAO.selectList(new QueryWrapper<>());

        List<BlogVO> blogVOS = blogDOS.stream().map(blogDO -> MapStruct.INSTANCE.BlogDOToBlogVO(blogDO))
                .collect(Collectors.toList());

        return blogVOS;
    }

    @Override
    public BlogVO queryBlogByMenuId(String menuId) {
        BlogDO blogDO = blogDAO.selectOne(new QueryWrapper<BlogDO>().eq("menu_id", menuId));

        return MapStruct.INSTANCE.BlogDOToBlogVO(blogDO);
    }

    @Override
    public int addBlog(BlogVO blogVO) {
        BlogDO blogDO = MapStruct.INSTANCE.BlogVOToBlogDO(blogVO);

        blogDO.setBlogId(UUID.randomUUID().toString());
        blogDO.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        blogDO.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        return blogDAO.insert(blogDO);
    }
}
