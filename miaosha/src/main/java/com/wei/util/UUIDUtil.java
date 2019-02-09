package com.wei.util;

import java.util.UUID;

/**
 * @author 为为
 * @create 1/26
 */
public class UUIDUtil {

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
