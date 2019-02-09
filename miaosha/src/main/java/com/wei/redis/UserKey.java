package com.wei.redis;
import com.wei.redis.BasePrefix;



/**
 * @author 为为
 * @create 11/27
 */
public class UserKey extends BasePrefix {


    private UserKey(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }
    private UserKey(String prefix) {
        super(prefix);
    }
    public  static UserKey getId = new UserKey("id-");
    public  static UserKey getName = new UserKey("name-");

}
