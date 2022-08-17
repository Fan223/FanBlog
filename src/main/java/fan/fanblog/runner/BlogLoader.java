package fan.fanblog.runner;

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
    private BlogService blogService;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<BlogVO> blogVOS = blogService.queryAllBlog().getRecords();
        List<String> blogIds = blogVOS.stream().map(blogVO -> blogVO.getBlogId()).collect(Collectors.toList());

        redisUtil.set("blogIds", blogIds);
    }
}
