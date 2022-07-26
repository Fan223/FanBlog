package fan.fanblog.blog.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BlogVO implements Serializable {

    private static final long serialVersionUID = -1L;

    private Integer id;
    private String title;
    private String content;
}
