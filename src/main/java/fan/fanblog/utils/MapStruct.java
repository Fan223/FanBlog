package fan.fanblog.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fan.fanblog.blog.entity.BlogDO;
import fan.fanblog.blog.vo.BlogVO;
import fan.fanblog.menu.entity.MenuDO;
import fan.fanblog.menu.vo.MenuVO;
import fan.fanblog.user.entity.UserDO;
import fan.fanblog.user.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = MapStructRule.class) // 整合 Spring，设置 componentModel = "spring"，需要使用的地方直接通过 @Resource 注入即可
public interface MapStruct {
    MapStruct INSTANCE = Mappers.getMapper(MapStruct.class);

    @Mapping(target = "createTime", source = "createTime", qualifiedByName = "toDate")
    @Mapping(target = "updateTime", source = "updateTime", qualifiedByName = "toDate")
    BlogVO BlogDOToBlogVO(BlogDO blogDO);

    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    BlogDO BlogVOToBlogDO(BlogVO blogVO);

    @Mapping(target = "createTime", source = "createTime", qualifiedByName = "toDate")
    @Mapping(target = "updateTime", source = "updateTime", qualifiedByName = "toDate")
    MenuVO MenuDOToMenuVO(MenuDO menuDO);

    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    MenuDO MenuVOToMenuDO(MenuVO menuVO);

    Page<BlogVO> BlogDOPageToBlogVOPage(Page<BlogDO> blogDOPage);

    List<MenuVO> MenuDOListToMenuVOList(List<MenuDO> menuDOList);

    UserVO UserDOToUserVO(UserDO userDO);
}
