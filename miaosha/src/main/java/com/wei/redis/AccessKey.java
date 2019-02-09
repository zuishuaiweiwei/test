
        package com.wei.redis;

/**
 * @author 为为
 * @create 11/27
 */
public class AccessKey extends BasePrefix {


    private AccessKey(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }
    private AccessKey(String prefix) {
        super(prefix);
    }

    public static final AccessKey ACCESS = new AccessKey("ac:");
    public static AccessKey withExpire(int expire){
        return new AccessKey("ac:",expire);
    }


}
