package fan.fanblog.user.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = -1L;

    private String username;
    private String password;
    private String captcha;
}
