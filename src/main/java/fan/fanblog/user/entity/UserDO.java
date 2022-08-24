package fan.fanblog.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("user")
@Data
public class UserDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String userId;
    private String username;
    private String password;
    private String avatar;
}
