package com.wei.redis;

import com.wei.redis.BasePrefix;


/**
 * @author 为为
 * @create 11/27
 */
public class GoodsKey extends BasePrefix {


    private GoodsKey(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }

    private GoodsKey(String prefix) {
        super(prefix);
    }

    public static final GoodsKey GoodsList = new GoodsKey("gl", 60);
    public static final GoodsKey GoodsDetail = new GoodsKey("gd:", 60);
    public static final GoodsKey GoodsStock = new GoodsKey("gs:");

}
