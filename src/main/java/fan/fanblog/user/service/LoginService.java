package fan.fanblog.user.service;

import fan.fanblog.user.vo.UserVO;

import java.io.IOException;
import java.util.Map;

public interface LoginService {
    Map login(UserVO userVO);

    String getCaptcha() throws IOException;
}
