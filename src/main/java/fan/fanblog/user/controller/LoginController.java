package fan.fanblog.user.controller;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.code.kaptcha.Producer;
import fan.fanblog.user.service.LoginService;
import fan.fanblog.user.vo.UserVO;
import fan.fanblog.utils.BlogResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@RestController
public class LoginController {
    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public BlogResult login(@RequestBody UserVO userVO) {
        Map loginResult = loginService.login(userVO);
        return BlogResult.success((String) loginResult.get("msg"), loginResult.get("data"));
    }

    @GetMapping("/api/getCaptcha")
    public BlogResult getCaptcha() throws IOException {
        return BlogResult.success(loginService.getCaptcha());
    }
}
