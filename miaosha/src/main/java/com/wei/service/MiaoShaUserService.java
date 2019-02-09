package com.wei.service;

import com.wei.dao.MiaoShaUserDao;
import com.wei.entity.MiaoShaUser;
import com.wei.exception.GlobalException;
import com.wei.redis.MiaoShaUserKey;
import com.wei.redis.RedisService;
import com.wei.result.CodeMsg;
import com.wei.util.Md5Util;
import com.wei.util.UUIDUtil;
import com.wei.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 为为
 * @create 1/25
 */
@Service
public class MiaoShaUserService {

    public static final String COOKIE_NAME = "token";
    @Autowired
    private MiaoShaUserDao miaoShaUserDao;
    @Autowired
    private RedisService redisService;

    public MiaoShaUser getById(String mobile){
        //取缓存
        MiaoShaUser user = redisService.get(MiaoShaUserKey.getById, mobile, MiaoShaUser.class);
        if(user != null){
            return user;
        }
        user = miaoShaUserDao.getById(mobile);
        if(user !=null){
            //设置缓存
            redisService.set(MiaoShaUserKey.getById,mobile,user);
        }
        return user;
    }

    /**
     * 更新用户密码
     * @param token
     * @param mobile
     * @param passwordNew
     * @return
     */
    public boolean updatePassword(String token ,String mobile,String passwordNew){
        MiaoShaUser user = getById(mobile);
        if(user == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        MiaoShaUser userNew = new MiaoShaUser();
        userNew.setId(mobile);
        userNew.setPassword(Md5Util.fromPassToDbPass(passwordNew,user.getSalt()));
        miaoShaUserDao.update(userNew);
        //处理缓存
        user.setPassword(userNew.getPassword());
        redisService.set(MiaoShaUserKey.getById,user.getId(),user);
        redisService.set(MiaoShaUserKey.token,token,user);

        return true;
    }
    public String login(HttpServletResponse response, LoginVo loginVo){
        if(loginVo == null){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //密码为空
        if(StringUtils.isEmpty(password)){
            throw new GlobalException(CodeMsg.PASSWORD_EMPTY);
        }
        //手机号为空
        if(StringUtils.isEmpty(mobile)){
            throw new GlobalException(CodeMsg.MOBILE_EMPTY);
        }
        //查找秒杀对象
        MiaoShaUser miaoShaUser = getById(mobile);
        //手机号码不存在
        if(null == miaoShaUser){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        String db = miaoShaUser.getPassword();
        String rel = Md5Util.fromPassToDbPass(password, miaoShaUser.getSalt());
        if(!db.equals(rel)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        String token = UUIDUtil.uuid();
        addCookie(response, miaoShaUser,token);
        return  token;
    }

    //分布式session
    public void addCookie(HttpServletResponse response, MiaoShaUser miaoShaUser, String token){
        //生成一个uuid，标记这个用户

        //存到redis中
        redisService.set(MiaoShaUserKey.token,token, miaoShaUser);
        //向浏览器添加cookie
        Cookie cookie = new Cookie(COOKIE_NAME,token);
        cookie.setMaxAge(MiaoShaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public MiaoShaUser getToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }

        MiaoShaUser miaoShaUser = redisService.get(MiaoShaUserKey.token, token, MiaoShaUser.class);
        if(miaoShaUser != null){
            addCookie(response, miaoShaUser,token);
        }
        return miaoShaUser;
    }
}
