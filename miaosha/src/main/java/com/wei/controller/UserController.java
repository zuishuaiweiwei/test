package com.wei.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;




        import com.wei.entity.MiaoShaUser;
        import com.wei.entity.User;
        import com.wei.redis.RedisService;
        import com.wei.redis.UserKey;
        import com.wei.result.CodeMsg;
        import com.wei.result.Result;
        import com.wei.service.MiaoShaUserService;
        import com.wei.service.UserService;
        import com.wei.vo.LoginVo;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.ResponseBody;

        import javax.servlet.http.HttpServletResponse;

/**
 * @author 为为
 * @create 11/23
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    MiaoShaUserService miaoShaUserService;


    @RequestMapping("/test")
    @ResponseBody
    public Result<MiaoShaUser> redisSet(MiaoShaUser user){
        return Result.success(user);
    }
}
