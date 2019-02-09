package com.wei.redis;
import com.wei.redis.BasePrefix;

/**
 * @author 为为
 * @create 11/27
 */
public class MiaoShaKey extends BasePrefix {


    private MiaoShaKey(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }
    private MiaoShaKey(String prefix) {
        super(prefix);
    }
    public  static final MiaoShaKey IS_OVER = new MiaoShaKey("over");
    public  static final MiaoShaKey GET_PATH = new MiaoShaKey("gp");
    public  static final MiaoShaKey VERIFY_CODE = new MiaoShaKey("vc",60);


}
