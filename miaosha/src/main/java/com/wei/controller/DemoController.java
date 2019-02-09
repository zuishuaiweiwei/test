package com.wei.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;




        import com.wei.entity.User;
        import com.wei.rabbitmq.MqSender;
        import com.wei.redis.RedisService;
        import com.wei.redis.UserKey;
        import com.wei.result.CodeMsg;
        import com.wei.result.Result;
        import com.wei.service.UserService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 为为
 * @create 11/23
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;
    @Autowired
    MqSender mqSender;

    @RequestMapping("/thymeleaf")
    @ResponseBody
    public Result thymeleaf(Model model){

        return Result.success("success");
    }
//    @RequestMapping("/mq")
//    @ResponseBody
//    public Result thymeleaf(){
//        mqSender.sender("hello world");
//        return Result.success("success");
//    }
//    @RequestMapping("/fanout")
//    @ResponseBody
//    public Result fanout(){
//        mqSender.fanoutSender("hello world");
//        return Result.success("success");
//    }
//
//    @RequestMapping("/topic")
//    @ResponseBody
//    public Result topic(){
//        mqSender.topicSender("hello world");
//        return Result.success("success");
//    }
//
//    @RequestMapping("/henders")
//    @ResponseBody
//    public Result henders(){
//        mqSender.hendersSender("hello world");
//        return Result.success("success");
//    }

    @RequestMapping("/success")
    @ResponseBody
    public Result<String> success(){
        return Result.success("success");
    }


    @RequestMapping("/error")
    @ResponseBody
    public Result<String> error(){
        return Result.error(CodeMsg.USER_ERROR);
    }

    @RequestMapping("/getDb")
    @ResponseBody
    public Result<User> getDb(){

        return Result.success(userService.getUserById(1));
    }
    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<String> redisGet(){
        String value = redisService.get(UserKey.getId,"1", String.class);
        return Result.success(value);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<String> redisSet(){
        User user = new User();
        user.setId(1);
        user.setName("weiwei");
        boolean flag = redisService.set(UserKey.getId,""+user.getId(),user.getName());
        String value = redisService.get(UserKey.getId,""+user.getId(),String.class);
        return Result.success(value);
    }
}
