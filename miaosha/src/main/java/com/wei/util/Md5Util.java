package com.wei.util;

import org.apache.commons.codec.digest.DigestUtils;

/**md5加密密码工具类
 * @author 为为
 * @create 1/25
 */
public class Md5Util {

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private final static String salt = "1a2b3c4d";

    /**
     * http协议中显示的经过加密的密码
     * @param src
     * @return
     */
    public static String inputPassFromPass(String src){
        String string = ""+salt.charAt(0)+salt.charAt(2) + src +salt.charAt(5) + salt.charAt(4);
        return md5(string);
    }

    /**
     * 数据库中存储的密码
     * @param src
     * @param salt
     * @return
     */
    public static String fromPassToDbPass(String src,String salt){
        String string =""+salt.charAt(0)+salt.charAt(2) + src +salt.charAt(5) + salt.charAt(4);
        return md5(string);
    }

    /**
     * 真正的密码存储到数据库中
     * @param src
     * @param salt
     * @return
     */
    public static String inputPassToDbPass(String src,String salt){
        return fromPassToDbPass(inputPassFromPass(src),salt);
    }

   /* public static void main(String []args){
        System.out.println(inputPassToDbPass("123123",salt));
    }*/
}
