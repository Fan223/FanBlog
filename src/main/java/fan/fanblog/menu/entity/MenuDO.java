package fan.fanblog.menu.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class MenuDO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String menuId; // 菜单ID
  private String parentId; // 父菜单ID
  private String menuName; // 菜单名称
  private String path; // 菜单路径
  private String permission; // 权限字符串
  private String component; // 组件名称
  private long type; // 类型
  private String icon; // 图标
  private long orderNum; // 排序号
  private long valiFlag; // 有效标志
  private java.sql.Timestamp createTime; // 创建时间
  private java.sql.Timestamp updateTime; // 更新时间
}
