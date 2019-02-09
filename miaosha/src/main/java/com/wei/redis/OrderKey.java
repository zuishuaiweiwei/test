package com.wei.redis;
import com.wei.redis.BasePrefix;



/**
 * @author 为为
 * @create 11/27
 */
public class OrderKey extends BasePrefix {


    private OrderKey(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }
    private OrderKey(String prefix) {
        super(prefix);
    }
    public  static final OrderKey order_goods_Key = new OrderKey("og");

}
