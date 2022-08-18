package fan.fanblog.menu.vo;

import fan.fanblog.menu.entity.MenuDO;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class MenuVO implements Serializable {

    private static final long serialVersionUID = -1L;

    private String menuId; // 菜单ID
    private String parentId; // 父菜单ID
    private String parentName; // 父菜单名称
    private String menuName; // 菜单名称
    private String path; // 菜单路径
    private String permission; // 权限字符串
    private String component; // 组件名称
    private int type; // 类型
    private String icon; // 图标
    private int orderNum; // 排序号
    private int valiFlag; // 有效标志
    private String createTime; // 创建时间
    private String updateTime; // 更新时间

    @Builder.Default
    private List<MenuVO> children = new ArrayList<>();
}
