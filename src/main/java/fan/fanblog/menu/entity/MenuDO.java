package fan.fanblog.menu.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MenuDO implements Serializable {
    private static final long serialVersionUID = -1L;

    private Integer id;
    private String title;
    private String content;
}
