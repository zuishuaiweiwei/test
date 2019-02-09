package com.wei.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



        import com.wei.entity.MiaoShaUser;
        import com.wei.entity.OrderInfo;
        import com.wei.entity.User;
        import com.wei.redis.RedisService;
        import com.wei.redis.UserKey;
        import com.wei.result.CodeMsg;
        import com.wei.result.Result;
        import com.wei.service.GoodsService;
        import com.wei.service.OrderService;
        import com.wei.service.UserService;
        import com.wei.vo.MiaoShaGoogsVo;
        import com.wei.vo.OrderDetailVo;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.*;

/**
 * @author 为为
 * @create 11/23
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;


    @RequestMapping(value = "/detail",method = RequestMethod.POST)
    @ResponseBody
    public Result<OrderDetailVo> detail(MiaoShaUser user, Model model, @RequestParam("infoId") String infoId){
        if(user ==null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo order = orderService.getOrderById(infoId);
        if(order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        Long goodsId = order.getGoodsId();
        MiaoShaGoogsVo goods = goodsService.getMiaoShaVoById("" + goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setMiaoShaGoogsVo(goods);
        vo.setOrderInfo(order);
        return Result.success(vo);
    }



}
