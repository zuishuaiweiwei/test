package com.wei.vo;

import com.wei.entity.OrderInfo;

/**
 * @author 为为
 * @create 2/6
 */
public class OrderDetailVo {
    private OrderInfo orderInfo;
    private MiaoShaGoogsVo miaoShaGoogsVo;

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public MiaoShaGoogsVo getMiaoShaGoogsVo() {
        return miaoShaGoogsVo;
    }

    public void setMiaoShaGoogsVo(MiaoShaGoogsVo miaoShaGoogsVo) {
        this.miaoShaGoogsVo = miaoShaGoogsVo;
    }
}
