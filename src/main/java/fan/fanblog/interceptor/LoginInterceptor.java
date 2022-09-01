package fan.fanblog.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import fan.fanblog.utils.BlogResult;
import fan.fanblog.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //  检查授权
        String authorization = request.getHeader("Authorization");
        String username = request.getHeader("username");

        if(StringUtils.isBlank(authorization) || StringUtils.isBlank(username)){
            setReturn(response);
            return false;
        } else {
            String jwt = (String) redisUtil.get("token-" + username);
            if (StringUtils.isBlank(jwt) || !authorization.equals(jwt)) {
                setReturn(response);
                return false;
            }
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    public void setReturn(HttpServletResponse response) throws IOException {
        String json = JSON.toJSONString(BlogResult.builder().code(401).msg("认证失败，请重新登录").build());
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(json.getBytes());
    }
}
