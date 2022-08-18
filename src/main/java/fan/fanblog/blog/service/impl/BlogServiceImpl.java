package fan.fanblog.blog.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fan.fanblog.blog.dao.BlogDAO;
import fan.fanblog.blog.dto.BlogDTO;
import fan.fanblog.blog.entity.BlogDO;
import fan.fanblog.blog.service.BlogService;
import fan.fanblog.menu.service.MenuService;
import fan.fanblog.menu.vo.MenuVO;
import fan.fanblog.utils.MapStruct;
import fan.fanblog.blog.vo.BlogVO;
import fan.fanblog.utils.RedisUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogDAO, BlogDO> implements BlogService {

    @Resource
    private BlogDAO blogDAO;

    @Resource
    private MenuService menuService;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public Page<BlogVO> queryBlog(BlogDTO blogDTO) {
        List<String> idList = menuService.getMenuIdByParentId(blogDTO.getCategory());
        QueryWrapper<BlogDO> blogDOQueryWrapper = new QueryWrapper<BlogDO>()
                .like(StringUtils.isNotBlank(blogDTO.getTitle()), "title", blogDTO.getTitle())
                .in(CollectionUtils.isNotEmpty(idList), "blog_id", idList);

        Page<BlogDO> blogDOPage = blogDAO.selectPage(new Page<>(blogDTO.getCurrent(), blogDTO.getSize()), blogDOQueryWrapper);

        return MapStruct.INSTANCE.BlogDOPageToBlogVOPage(blogDOPage);
    }

    @Override
    public BlogVO queryBlogByMenuId(String menuId) {
        BlogDO blogDO = blogDAO.selectOne(new QueryWrapper<BlogDO>().eq("blog_id", menuId));

        return MapStruct.INSTANCE.BlogDOToBlogVO(blogDO);
    }

    private List<String> getBlogIds() {
        List<String> blogIds = (List<String>) redisUtil.get("blogIds");
        if (CollectionUtils.isEmpty(blogIds)) {
            blogIds = blogDAO.selectList(new QueryWrapper<>()).stream().map(blogDO -> blogDO.getBlogId()).collect(Collectors.toList());
            redisUtil.set("blogIds", blogIds);
        }
        return blogIds;
    }

    @Transactional
    @Override
    public int addBlog(BlogVO blogVO) {
        List<String> blogIds = getBlogIds();
        if (blogIds.contains(blogVO.getBlogId())) {
            menuService.updateParentId(blogVO.getBlogId(), blogVO.getParentId());
            return updateBlog(blogVO);
        }

        int addResult = saveOrAddBlogAndMenu(blogVO, blogIds, "add");
        return addResult;
    }

    @Transactional
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

    @Override
    public int deleteBlog(ArrayList<String> idList) {
        int deleteResult = blogDAO.deleteBatchIds(idList);
        menuService.deleteMenu(idList);

        return deleteResult;
    }

    private int saveOrAddBlogAndMenu(BlogVO blogVO, List<String> blogIds, String flag) {
        // 设置统一的 Id 和 时间
        String blogId = UUID.randomUUID().toString();
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        // 添加博客对应的菜单
        if (flag.equals("save")) {
            MenuVO menuVO = MenuVO.builder().menuId(blogId).parentId("fb90460f-43b4-4225-bc23-9380a13bd813")
                    .menuName(blogVO.getTitle()).path("/blog/preview").component("blog/Preview")
                    .type(3).orderNum(1).build();
            menuService.addMenu(menuVO);
        } else {
            MenuVO menuVO = MenuVO.builder().menuId(blogId).parentId(blogVO.getParentId())
                    .menuName(blogVO.getTitle()).path("/blog/preview").component("blog/Preview")
                    .type(3).orderNum(1).build();
            menuService.addMenu(menuVO);
        }

        // 添加博客
        BlogDO blogDO = MapStruct.INSTANCE.BlogVOToBlogDO(blogVO);
        blogDO.setBlogId(blogId);
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
