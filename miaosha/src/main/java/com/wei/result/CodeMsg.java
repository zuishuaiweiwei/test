
        package com.wei.result;

/**
 * @author 为为
 * @create 11/23
 */
public class CodeMsg {

    private int code;
    private String msg;
    /**
     * 通用模块
     */
    public static final CodeMsg SUCCESS = new CodeMsg(100,"success");
    public static final CodeMsg USER_ERROR = new CodeMsg(101,"用户错误");
    public static final CodeMsg Bind_ERROR = new CodeMsg(102,"参数校验错误：%s");
    public static final CodeMsg SERVER_ERROR = new CodeMsg(103,"服务端错误");
    public static final CodeMsg REQUEST_LLLEGAL = new CodeMsg(104,"服务端错误");
    public static final CodeMsg FREQUENT_VISITS = new CodeMsg(105,"访问太频繁");
    /**
     * 登陆模块
     */
    public static final CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
    public static final CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "登录密码不能为空");
    public static final CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空");
    public static final CodeMsg MOBILE_ERROR = new CodeMsg(500213, "手机号格式错误");
    public static final CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "手机号不存在");
    public static final CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误");

    /**
     * 秒杀模块
     */
    public static final CodeMsg MIAOSHA_OVER = new CodeMsg(500300, "秒杀失败");
    public static final CodeMsg MIAOSHA_REPEATE = new CodeMsg(500300, "重复秒杀");
    public static final CodeMsg MIAOSHA_FAIL = new CodeMsg(500300, "秒杀失败");
    public static final CodeMsg VERIFY_CODE_ERROR = new CodeMsg(500300, "验证码错误");

    /**
     *订单模块
     */
    public static final CodeMsg ORDER_NOT_EXIST = new CodeMsg(500400, "订单不存在");


    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public CodeMsg fillArgs(Object... object){
        int code = this.code;
        String message = String.format(this.msg,object);
        return new CodeMsg(code,message);
    }

    public String getMsg() {
        return msg;
    }

}
