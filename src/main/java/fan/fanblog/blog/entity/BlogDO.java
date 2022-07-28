package fan.fanblog.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("blog")
public class BlogDO implements Serializable {
    private static final long serialVersionUID = -1L;

    @TableId
    private Integer id;
    private String title;
    private String content;
}
