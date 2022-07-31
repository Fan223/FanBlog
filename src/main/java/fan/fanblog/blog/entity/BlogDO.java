package fan.fanblog.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;

@Data
@TableName("blog")
public class BlogDO implements Serializable {
    private static final long serialVersionUID = -1L;

    @TableId
    private String blogId;
    private String title;
    private String content;
    private Timestamp createTime;
    private Timestamp updateTime;
}
