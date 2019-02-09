package com.wei.service;

import com.wei.dao.OrderDao;
import com.wei.entity.MiaoShaUser;
import com.wei.entity.MiaoshaOrder;
import com.wei.entity.OrderInfo;
import com.wei.redis.OrderKey;
import com.wei.redis.RedisService;
import com.wei.vo.MiaoShaGoogsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author 为为
 * @create 1/31
 */
@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private RedisService redisService;
    /**
     * 有没有重复秒杀
     * @param userId
     * @param goodsId
     * @return
     */
    public  MiaoshaOrder getOrderByUserIdGoodsId(String userId, String goodsId) {
        return redisService.get(OrderKey.order_goods_Key, userId + "_" + goodsId, MiaoshaOrder.class);
    }

    /**
     * 创建订单
     * @param user
     * @param goods
     * @return
     */
    @Transactional
    public OrderInfo createOrder(MiaoShaUser user, MiaoShaGoogsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setOrderChannel(0);
        orderInfo.setStatus(0);
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setUserId(Long.valueOf(user.getId()));
        orderDao.createOrder(orderInfo);

        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setUserId(Long.valueOf(user.getId()));
        orderDao.createMiaoShaOrder(miaoshaOrder);

        redisService.set(OrderKey.order_goods_Key,user.getId()+"_"+goods.getId(),miaoshaOrder);
        return orderInfo;
    }

    public OrderInfo getOrderById(String infoId) {
        return orderDao.getOrderById(infoId);
    }
}
