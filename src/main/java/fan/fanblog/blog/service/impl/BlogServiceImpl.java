package fan.fanblog.blog.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fan.fanblog.blog.dao.BlogDAO;
import fan.fanblog.blog.entity.BlogDO;
import fan.fanblog.blog.service.BlogService;
import fan.fanblog.menu.dao.MenuDAO;
import fan.fanblog.menu.entity.MenuDO;
import fan.fanblog.runner.BlogLoader;
import fan.fanblog.utils.MapStruct;
import fan.fanblog.blog.vo.BlogVO;
import fan.fanblog.utils.RedisUtil;
import org.apache.commons.collections4.CollectionUtils;
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

    @Resource
    private MenuDAO menuDAO;

    @Resource
    private RedisUtil redisUtil;

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
//        if (BlogLoader.blogIds.contains(blogVO.getBlogId())) {
//            return this.updateBlog(blogVO);
//        }

        String uuid = UUID.randomUUID().toString();

        BlogDO blogDO = MapStruct.INSTANCE.BlogVOToBlogDO(blogVO);
        blogDO.setBlogId(uuid);
        blogDO.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        blogDO.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));

        return blogDAO.insert(blogDO);
    }

    @Override
    public String saveBlog(BlogVO blogVO) {
        List<String> blogIds = (List<String>) redisUtil.get("blogIds");
        if (CollectionUtils.isEmpty(blogIds)) {
            blogIds = queryAllBlog().stream().map(blogVO1 -> blogVO1.getBlogId()).collect(Collectors.toList());
            redisUtil.set("blogIds", blogIds);
        }

        if (blogIds.contains(blogVO.getBlogId())) {
            updateBlog(blogVO);
            return blogVO.getBlogId();
        }

        // 设置统一的 menuId 和 时间
        String blogId = UUID.randomUUID().toString();
        String menuId = UUID.randomUUID().toString();
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        // 添加博客对应的菜单
        MenuDO menuDO = MenuDO.builder().menuId(menuId)
                .parentId("0bd8151e-594c-4aa3-abf8-0ad6b29bc832")
                .menuName(blogVO.getTitle())
                .path("/blog/preview")
                .component("blog/Preview")
                .type(3)
                .orderNum(1)
                .valiFlag(1)
                .createTime(timestamp)
                .updateTime(timestamp)
                .build();
        menuDAO.insert(menuDO);

        // 添加博客
        BlogDO blogDO = MapStruct.INSTANCE.BlogVOToBlogDO(blogVO);
        blogDO.setBlogId(blogId);
        blogDO.setMenuId(menuId);
        blogDO.setCreateTime(timestamp);
        blogDO.setUpdateTime(timestamp);

        blogDAO.insert(blogDO);
        blogIds.add(blogId);
        redisUtil.set("blogIds", blogIds);

        return blogId;
    }

    public int updateBlog(BlogVO blogVO) {
        BlogDO blogDO = MapStruct.INSTANCE.BlogVOToBlogDO(blogVO);
        blogDO.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));

        return blogDAO.updateById(blogDO);
    }
}
