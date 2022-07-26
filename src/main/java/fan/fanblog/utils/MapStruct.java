package fan.fanblog.utils;

import fan.fanblog.entity.BlogDO;
import fan.fanblog.vo.BlogVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // 整合 Spring，设置 componentModel = "spring"，需要使用的地方直接通过 @Resource 注入即可
public interface MapStruct {

    MapStruct INSTANCE = Mappers.getMapper(MapStruct.class);

//    @Mapping(source = "userDOGender", target = "userDTOGender")
//    UserDTO userDOToUserDTO(UserDO userDO);

    BlogVO BlogDOToBlogVO(BlogDO personDO);

    BlogDO BlogVOToBlogDO(BlogVO personDTO);
}
