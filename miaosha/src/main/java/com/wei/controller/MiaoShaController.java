package com.wei.controller;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;



        import com.wei.entity.MiaoShaUser;
        import com.wei.entity.MiaoshaOrder;
        import com.wei.interceptor.AccessLimit;
        import com.wei.rabbitmq.MiaoshaMessage;
        import com.wei.rabbitmq.MqSender;
        import com.wei.redis.GoodsKey;
        import com.wei.redis.RedisService;
        import com.wei.result.CodeMsg;
        import com.wei.result.Result;
        import com.wei.service.GoodsService;
        import com.wei.service.MiaoShaService;
        import com.wei.service.MiaoShaUserService;
        import com.wei.service.OrderService;
        import com.wei.vo.MiaoShaGoogsVo;
        import org.springframework.beans.factory.InitializingBean;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.*;

        import javax.imageio.ImageIO;
        import javax.servlet.http.HttpServletResponse;
        import java.awt.image.BufferedImage;
        import java.io.OutputStream;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

/**
 * 商品页面控制
 *
 * @author 为为
 * @create 1/25
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoShaController implements InitializingBean {

    @Autowired
    private MiaoShaUserService miaoShaUserService;
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private MiaoShaService miaoShaService;
    @Autowired
    private RedisService redisService;

    @Autowired
    private MqSender sender;

    private Map<Long, Boolean> isOver = new HashMap(16);

    @Override
    public void afterPropertiesSet() throws Exception {
        List<MiaoShaGoogsVo> list = goodsService.getList();
        if (list.size() <= 0) {
            return;
        }
        for (MiaoShaGoogsVo goods : list) {
            redisService.set(GoodsKey.GoodsStock, goods.getId() + "", goods.getStockCount());
            //生成内存标记
            isOver.put(goods.getId(), false);
        }
    }

    /**
     * 获取秒杀结果
     *
     * @param user
     * @param model
     * @param goodsId
     * @return 0是正在排队，-1是已经结束，商品ID是成功秒杀
     */
    @RequestMapping("/result")
    @ResponseBody
    public Result<Long> getResult(MiaoShaUser user, Model model, @RequestParam("goodsId") String goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //获取rabbitmq的处理结果
        Long ret = miaoShaService.getResult(user, goodsId);
        return Result.success(ret);
    }

    /**
     * 生成秒杀订单
     *
     * @param user
     * @param model
     * @param goodsId
     * @return 没用缓存之前
     * QPS: 366
     * 3000 * 5
     * <p>
     * 用redis缓存
     * QPS: 624
     * 3000 * 5
     * <p>
     * redis + rabbit 进一步优化
     * QPS: 1000
     * 3000 * 5
     */
    @RequestMapping("/{path}/do_miaosha")
    @ResponseBody
    public Result<Integer> doMiaosha(MiaoShaUser user, Model model, @RequestParam("goodsId") String goodsId, @PathVariable("path") String path) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //放恶意刷接口
        boolean isPath = miaoShaService.checkPath(user.getId(), goodsId, path);
        if (!isPath) {
            return Result.error(CodeMsg.REQUEST_LLLEGAL);
        }

        //内存标记 是否秒杀结束
        Boolean flag = isOver.get(Long.valueOf(goodsId));
        if (flag) {
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }
        //预减库存
        Long stock = redisService.decr(GoodsKey.GoodsStock, goodsId);
        if (stock < 0) {
            isOver.put(Long.valueOf(goodsId), true);
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }
        //是否重复秒杀
        MiaoshaOrder order = orderService.getOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.MIAOSHA_REPEATE);
        }
        //入队
        sender.senderMiaoshaMessage(new MiaoshaMessage(user, Long.valueOf(goodsId)));
        return Result.success(0);
    }

    /**
     * 隐藏秒杀地址
     *
     * AccessLimit 自己定义注释 防止恶意刷接口做的限流
     * seconds 标记存活时间
     * maxCount 最多访问几次
     * needLogin 需不需要登陆
     * @param user
     * @param goodsId
     * @return
     */

    @AccessLimit(seconds = 5,maxCount = 5,needLogin = true)
    @RequestMapping(value = "/getPath")
    @ResponseBody
    public Result<String> getPath(MiaoShaUser user, @RequestParam("goodsId") String goodsId,
                                  @RequestParam(value = "verifyCode",defaultValue = "0") String verifyCode) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        boolean flag = miaoShaService.checkVerifyCode(user.getId(),goodsId,verifyCode);
        if(!flag){
            return Result.error(CodeMsg.VERIFY_CODE_ERROR);
        }
        String path = miaoShaService.createPath(user.getId(), goodsId);
        return Result.success(path);
    }

    /**
     * 生成验证码图片
     * @param response
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getMiaoshaVerifyCod(HttpServletResponse response, MiaoShaUser user,
                                              @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        try {
            BufferedImage image = miaoShaService.createVerifyCode(user, goodsId);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.MIAOSHA_FAIL);
        }
    }
}
