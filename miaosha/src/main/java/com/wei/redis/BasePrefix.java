
        package com.wei.redis;

/**
 * @author 为为
 * @create 11/27
 */
public abstract class BasePrefix implements KeyPrefix {

    /**
     * 前缀
     */
    private String prefix;
    /**
     * 过期时间
     */
    private int expireSeconds;

    public BasePrefix(String prefix,int expireSeconds){
        this.prefix = prefix;
        this.expireSeconds = expireSeconds;
    }

    public BasePrefix(String prefix) {

        this(prefix,0);
    }

    @Override
    public String getPrefix() {
        String simpleName = getClass().getSimpleName();
        return simpleName+":"+prefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }
}
