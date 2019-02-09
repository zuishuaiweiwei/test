package com.wei.redis;
import com.wei.redis.BasePrefix;




/**
 * @author 为为
 * @create 11/27
 */
public class MiaoShaUserKey extends BasePrefix {

    private static final int TOKEN_EXPIRE = 3600*24 *2;

    private MiaoShaUserKey(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }
    private MiaoShaUserKey(String prefix) {
        super(prefix);
    }
    public  static final MiaoShaUserKey token = new MiaoShaUserKey("tk",TOKEN_EXPIRE);
    public  static final MiaoShaUserKey getById = new MiaoShaUserKey("id");

}
