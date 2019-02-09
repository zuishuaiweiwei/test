package com.wei.interceptor;

import com.alibaba.fastjson.JSON;
import com.wei.entity.MiaoShaUser;
import com.wei.redis.AccessKey;
import com.wei.redis.RedisService;
import com.wei.result.CodeMsg;
import com.wei.result.Result;
import com.wei.service.MiaoShaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 为为
 * @create 2/8
 */
@Service
public class AccessInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    MiaoShaUserService userService;

    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            MiaoShaUser user = getUser(request, response);
            UserContext.setUser(user);
            HandlerMethod hd = (HandlerMethod) handler;
            AccessLimit access = hd.getMethodAnnotation(AccessLimit.class);
            if(access == null){
                return true;
            }
            int seconds = access.seconds();
            int maxCount = access.maxCount();
            boolean needLogin = access.needLogin();

            if (needLogin) {
                if (user == null) {
                    render(request, response, CodeMsg.SESSION_ERROR);
                } else {
                    String uri = request.getRequestURI();
                    String key = uri + "-" + user.getId();
                    Integer count = redisService.get(AccessKey.ACCESS, key, Integer.class);
                    if (count == null) {
                        redisService.set(AccessKey.withExpire(seconds), key, 1);
                        return true;
                    } else if (count < maxCount) {
                        redisService.incr(AccessKey.ACCESS, key);
                        return true;
                    } else {
                        render(request, response, CodeMsg.FREQUENT_VISITS);
                        return false;
                    }
                }
            }
            return true;
        }
        return true;
    }

    private String getCookieValue(HttpServletRequest request, String cookiName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookiName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private void render(HttpServletRequest request, HttpServletResponse response, CodeMsg cm) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream out = response.getOutputStream();
        String ret = JSON.toJSONString(Result.error(cm));
        out.write(ret.getBytes("utf-8"));
        out.flush();
        out.close();

    }

    private MiaoShaUser getUser(HttpServletRequest request, HttpServletResponse response) {
        String paramToken = request.getParameter(MiaoShaUserService.COOKIE_NAME);
        String cookieToken = getCookieValue(request, MiaoShaUserService.COOKIE_NAME);
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        return userService.getToken(response, token);
    }
}
