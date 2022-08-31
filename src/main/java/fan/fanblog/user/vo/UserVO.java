package fan.fanblog.user.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Named;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserVO implements Serializable {

    private static final long serialVersionUID = -1L;

    private String username;
    private String password;
    private String captcha;
    private String jwt;

    public UserVO(String username, String jwt) {
        this.username = username;
        this.jwt = jwt;
    }
}
