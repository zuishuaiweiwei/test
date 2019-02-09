
        package com.wei.redis;

/**
 * @author 为为
 * @create 11/27
 */
public interface KeyPrefix {
    /**
     *  获取key的前缀
     * @return
     */
    String getPrefix();

    /**
     * 设置key的过期时间
     * @return
     */
    int expireSeconds();
}
