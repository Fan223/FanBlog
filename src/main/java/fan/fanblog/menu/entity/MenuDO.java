package fan.fanblog.menu.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("menu")
public class MenuDO implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId
  private String menuId; // 菜单ID
  private String parentId; // 父菜单ID
  private String menuName; // 菜单名称
  private String path; // 菜单路径
  private String permission; // 权限字符串
  private String component; // 组件名称
  private int type; // 类型
  private String icon; // 图标
  private int orderNum; // 排序号
  private int valiFlag; // 有效标志
  private java.sql.Timestamp createTime; // 创建时间
  private java.sql.Timestamp updateTime; // 更新时间
}
