package fan.fanblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fan.fanblog.dao.BlogDAO;
import fan.fanblog.entity.BlogDO;
import fan.fanblog.service.BlogService;
import fan.fanblog.utils.MapStruct;
import fan.fanblog.vo.BlogVO;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

public class BlogServiceImpl implements BlogService {

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
