package fan.fanblog.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fan.fanblog.blog.dao.BlogDAO;
import fan.fanblog.blog.entity.BlogDO;
import fan.fanblog.blog.service.BlogService;
import fan.fanblog.utils.MapStruct;
import fan.fanblog.blog.vo.BlogVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public int addBlog(BlogVO blogVO) {

        return blogDAO.insert(MapStruct.INSTANCE.BlogVOToBlogDO(blogVO));
    }


}