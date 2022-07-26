package fan.fanblog.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BlogDO implements Serializable {
    private static final long serialVersionUID = -1L;

    private Integer id;
    private String title;
    private String content;
}
