package fan.fanblog.blog.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class BlogVO implements Serializable {

    private static final long serialVersionUID = -1L;

    private String blogId;
    private String menuName;
    private String parentId;
    private String title;
    private String content;
    private int orderNum;
    private String createTime;
    private String updateTime;
}
