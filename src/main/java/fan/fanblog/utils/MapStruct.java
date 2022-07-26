package fan.fanblog.utils;

import fan.fanblog.blog.entity.BlogDO;
import fan.fanblog.blog.vo.BlogVO;
import fan.fanblog.menu.entity.MenuDO;
import fan.fanblog.menu.vo.MenuVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // 整合 Spring，设置 componentModel = "spring"，需要使用的地方直接通过 @Resource 注入即可
public interface MapStruct {

    MapStruct INSTANCE = Mappers.getMapper(MapStruct.class);

//    @Mapping(source = "userDOGender", target = "userDTOGender")
//    UserDTO userDOToUserDTO(UserDO userDO);

    BlogVO BlogDOToBlogVO(BlogDO blogDO);

    BlogDO BlogVOToBlogDO(BlogVO blogVO);

    MenuVO MenuDOToMenuVO(MenuDO menuDO);

    MenuDO MenuVOToMenuDO(MenuVO menuVO);
}
