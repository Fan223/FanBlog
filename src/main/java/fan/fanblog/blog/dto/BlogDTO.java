package fan.fanblog.blog.dto;

import lombok.Data;

/**
 * @Classname BlogDTO
 * @Description TODO
 * @Date 2022/8/17 22:29
 * @Author Fan
 */
@Data
public class BlogDTO {
    private String title;
    private int current = 1;
    private int size = 5;
}
