package com.wei.service;
import com.wei.entity.MiaoShaUser;
import com.wei.entity.MiaoshaOrder;
import com.wei.entity.OrderInfo;
import com.wei.redis.MiaoShaKey;
import com.wei.redis.RedisService;
import com.wei.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;



        import com.wei.entity.MiaoShaUser;
        import com.wei.entity.MiaoshaOrder;
        import com.wei.entity.OrderInfo;
        import com.wei.redis.MiaoShaKey;
        import com.wei.redis.RedisService;
        import com.wei.result.CodeMsg;
        import com.wei.result.Result;
        import com.wei.util.Md5Util;
        import com.wei.util.UUIDUtil;
        import com.wei.vo.MiaoShaGoogsVo;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;

        import javax.script.ScriptEngine;
        import javax.script.ScriptEngineManager;
        import java.awt.*;
        import java.awt.image.BufferedImage;
        import java.util.Random;

/**
 * @author 为为
 * @create 1/31
 */
@Service
public class MiaoShaService {


    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;
    @Autowired
    RedisService redisService;

    /**
     * 真正的秒杀
     * @param user
     * @param goods
     * @return
     */
    @Transactional
    public OrderInfo miaosha(MiaoShaUser user, MiaoShaGoogsVo goods) {
        boolean flag = goodsService.reduceStock(goods);
        if (flag) {
            return orderService.createOrder(user, goods);
        } else {
            setIsOver(goods.getId());
            return null;
        }
    }

    /**
     * 获取秒杀结果
     * @param user
     * @param goodsId
     * @return
     */
    public long getResult(MiaoShaUser user, String goodsId) {
        MiaoshaOrder miaoshaOrder = orderService.getOrderByUserIdGoodsId(user.getId(), goodsId);
        if (miaoshaOrder != null) {
            return miaoshaOrder.getOrderId();
        } else {
            boolean isOver = getIsOver(goodsId);
            if (isOver) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private boolean getIsOver(String goodsId) {
        return redisService.exists(MiaoShaKey.IS_OVER, goodsId);

    }


    public void setIsOver(Long goodsId) {
        redisService.set(MiaoShaKey.IS_OVER, goodsId + "", true);
    }

    /**
     * 创建秒杀地址
     * @param userId
     * @param goodsId
     * @return
     */
    public String createPath(String userId, String goodsId) {
        String str = Md5Util.md5(UUIDUtil.uuid() + "123456");
        redisService.set(MiaoShaKey.GET_PATH,userId+"-"+goodsId,str);
        return str;
    }

    /**
     * 验证秒杀地址对不对
     * @param userId
     * @param goodsId
     * @param path
     * @return
     */
    public boolean checkPath(String userId, String goodsId, String path) {
        String oldPath = redisService.get(MiaoShaKey.GET_PATH, userId+ "-" + goodsId, String.class);

        return path.equals(oldPath);

    }

    /**
     * 生成验证码图片
     * @param user
     * @param goodsId
     * @return
     */
    public BufferedImage createVerifyCode(MiaoShaUser user, long goodsId) {
        if(user == null || goodsId <=0) {
            return null;
        }
        int width = 80;
        int height = 32;
        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // generate a random code
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        //把验证码存到redis中
        int rnd = calc(verifyCode);
        redisService.set(MiaoShaKey.VERIFY_CODE, user.getId()+"-"+goodsId, rnd);
        //输出图片
        return image;
    }

    /**
     * 计算验证码结果存到redis中
     * @param exp
     * @return
     */
    private static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer)engine.eval(exp);
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static char[] ops = new char[] {'+', '-', '*'};

    /**
     * 生成验证码数学公式
     * @param rdm
     * @return
     */
    private String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        String exp = ""+ num1 + op1 + num2 + op2 + num3;
        return exp;
    }

    /**
     * 检查验证码是否正确
     * @param userId
     * @param goodsId
     * @param verifyCode
     * @return
     */
    public boolean checkVerifyCode(String userId,String goodsId, String verifyCode) {
        if(userId == null || goodsId ==null) {
            return false;
        }
        Integer codeOld = redisService.get(MiaoShaKey.VERIFY_CODE,userId+"-"+goodsId,Integer.class);
        if(codeOld == null || codeOld - Integer.valueOf(verifyCode) != 0){
            redisService.del(MiaoShaKey.VERIFY_CODE,userId+"-"+goodsId);
            return false;
        }
        return true;
    }
}
