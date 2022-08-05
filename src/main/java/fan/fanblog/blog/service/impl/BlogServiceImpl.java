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
import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
        List<MenuDO> menuDOS = menuDAO.selectList(new QueryWrapper<>());
        Map<String, MenuDO> menuMap = menuDOS.stream().collect(Collectors.toMap(MenuDO::getMenuId, MenuDO -> MenuDO));

        List<BlogVO> blogVOS = blogDOS.stream().map(blogDO -> {
                    BlogVO blogVO = MapStruct.INSTANCE.BlogDOToBlogVO(blogDO);
                    if (menuMap.containsKey(blogDO.getMenuId())) {
                        blogVO.setMenuName(menuMap.get(menuMap.get(blogDO.getMenuId()).getParentId()).getMenuName());
                    }
                    return blogVO;
                }).collect(Collectors.toList());

        return blogVOS;
    }

    @Override
    public BlogVO queryBlogByMenuId(String menuId) {
        BlogDO blogDO = blogDAO.selectOne(new QueryWrapper<BlogDO>().eq("menu_id", menuId));

        return MapStruct.INSTANCE.BlogDOToBlogVO(blogDO);
    }

    private List<String> getBlogIds() {
        List<String> blogIds = (List<String>) redisUtil.get("blogIds");
        if (CollectionUtils.isEmpty(blogIds)) {
            blogIds = queryAllBlog().stream().map(blogVO -> blogVO.getBlogId()).collect(Collectors.toList());
            redisUtil.set("blogIds", blogIds);
        }
        return blogIds;
    }

    @Override
    public int addBlog(BlogVO blogVO) {
        List<String> blogIds = getBlogIds();
        if (blogIds.contains(blogVO.getBlogId())) {
            menuDAO.updateParentId(blogVO.getMenuId(), blogVO.getParentId());
            return updateBlog(blogVO);
        }

        int addResult = saveOrAddBlogAndMenu(blogVO, blogIds, "add");
        return addResult;
    }

    @Override
    public BlogVO saveBlog(BlogVO blogVO) {
        List<String> blogIds = getBlogIds();
        if (blogIds.contains(blogVO.getBlogId())) {
            updateBlog(blogVO);
            return blogVO;
        }

        saveOrAddBlogAndMenu(blogVO, blogIds, "save");
        return blogVO;
    }

    private int saveOrAddBlogAndMenu(BlogVO blogVO, List<String> blogIds, String flag) {
        // 设置统一的 Id 和 时间
        String blogId = UUID.randomUUID().toString();
        String menuId = UUID.randomUUID().toString();
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        // 添加博客对应的菜单
        if (flag.equals("save")) {
            MenuDO menuDO = MenuDO.builder().menuId(menuId).parentId("fb90460f-43b4-4225-bc23-9380a13bd813")
                    .menuName(blogVO.getTitle()).path("/blog/preview").component("blog/Preview")
                    .type(3).orderNum(1).valiFlag(1).createTime(timestamp).updateTime(timestamp).build();
            menuDAO.insert(menuDO);
        } else {
            MenuDO menuDO = MenuDO.builder().menuId(menuId).parentId(blogVO.getParentId())
                    .menuName(blogVO.getTitle()).path("/blog/preview").component("blog/Preview")
                    .type(3).orderNum(1).valiFlag(1).createTime(timestamp).updateTime(timestamp).build();
            menuDAO.insert(menuDO);
        }

        // 添加博客
        blogVO.setBlogId(blogId);
        blogVO.setMenuId(menuId);
        BlogDO blogDO = MapStruct.INSTANCE.BlogVOToBlogDO(blogVO);
        blogDO.setCreateTime(timestamp);
        blogDO.setUpdateTime(timestamp);

        int addResult = blogDAO.insert(blogDO);
        blogIds.add(blogId);
        redisUtil.set("blogIds", blogIds);

        return addResult;
    }

    public int updateBlog(BlogVO blogVO) {
        BlogDO blogDO = MapStruct.INSTANCE.BlogVOToBlogDO(blogVO);
        blogDO.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));

        return blogDAO.updateById(blogDO);
    }
}
