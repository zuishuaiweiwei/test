package com.wei.interceptor;
import com.wei.entity.MiaoShaUser;



import com.wei.entity.MiaoShaUser;

/**
 * @author 为为
 * @create 2/8
 */
public class UserContext {

    private static ThreadLocal<MiaoShaUser> threadLocal = new ThreadLocal();

    public static void setUser(MiaoShaUser user) {
        threadLocal.set(user);
    }

    public static MiaoShaUser getUser() {
        return threadLocal.get();
    }
}
