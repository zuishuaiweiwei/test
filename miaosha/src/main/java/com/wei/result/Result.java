package com.wei.result;
import com.wei.result.CodeMsg;



/**
 * @author 为为
 * @create 11/23
 */
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    public static final int  USER_ERROR = 100;
    public static final int  USER_SUCCESS = 100;
    private  Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(CodeMsg cm) {
        if(cm==null){
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }

    public Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    public static Result error(CodeMsg cm){
        return new Result(cm);
    }

    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
