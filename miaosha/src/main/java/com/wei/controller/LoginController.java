package com.wei.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;




        import com.wei.result.Result;
        import com.wei.service.MiaoShaUserService;
        import com.wei.vo.LoginVo;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.ResponseBody;

        import javax.servlet.http.HttpServletResponse;
        import javax.validation.Valid;

/**登陆控制
 * @author 为为
 * @create 1/25
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private MiaoShaUserService miaoShaUserService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login.html";
    }

    @RequestMapping("do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVo loginVo){

        String token = miaoShaUserService.login(response,loginVo);
        return Result.success(token);
    }
}
