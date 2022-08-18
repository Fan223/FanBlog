package fan.fanblog.runner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fan.fanblog.blog.dao.BlogDAO;
import fan.fanblog.blog.entity.BlogDO;
import fan.fanblog.blog.service.BlogService;
import fan.fanblog.blog.vo.BlogVO;
import fan.fanblog.menu.service.MenuService;
import fan.fanblog.utils.RedisUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BlogLoader implements ApplicationRunner {

    @Resource
    private BlogDAO blogDAO;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<BlogDO> blogDOS = blogDAO.selectList(new QueryWrapper<>());
        List<String> blogIds = blogDOS.stream().map(blogDO -> blogDO.getBlogId()).collect(Collectors.toList());

        redisUtil.set("blogIds", blogIds);
    }
}
