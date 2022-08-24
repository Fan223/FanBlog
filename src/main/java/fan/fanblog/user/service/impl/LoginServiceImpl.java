package fan.fanblog.user.service.impl;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.Producer;
import fan.fanblog.user.dao.UserDAO;
import fan.fanblog.user.entity.UserDO;
import fan.fanblog.user.service.LoginService;
import fan.fanblog.user.vo.UserVO;
import fan.fanblog.utils.MapStruct;
import fan.fanblog.utils.RedisUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserDAO userDAO;

    @Resource
    private Producer producer;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public Map login(UserVO userVO) {
        String captcha = (String) redisUtil.get("captcha");
        if (!captcha.equals(userVO.getCaptcha())) {
            return MapUtil.builder().put("data", null).put("msg", "验证码错误").build();
        }
        UserDO userDO = userDAO.selectOne(new QueryWrapper<UserDO>().eq("username", userVO.getUsername()));

        if (ObjectUtil.isNotEmpty(userDO) && userDO.getPassword().equals(userVO.getPassword())) {
            return MapUtil.builder().put("data", MapStruct.INSTANCE.UserDOToUserVO(userDO)).put("msg", "登录成功").build();
        }
        return MapUtil.builder().put("data", null).put("msg", "用户名或密码错误").build();
    }

    @Override
    public String getCaptcha() throws IOException {
        String captcha = producer.createText(); // 生成验证码字符串
        BufferedImage image = producer.createImage(captcha);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream); // 生成图片字节数组
        Base64Encoder base64Encoder = new Base64Encoder(); // 转换为base64，生成图片验证码
        String captchaImg = "data:image/jpg;base64," + base64Encoder.encode(byteArrayOutputStream.toByteArray());

        redisUtil.set("captcha", captcha, 120);
        return captchaImg;
    }
}
