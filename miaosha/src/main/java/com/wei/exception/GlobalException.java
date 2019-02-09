package com.wei.exception;

        import com.wei.result.CodeMsg;

/**
 * @author 为为
 * @create 1/26
 */
public class GlobalException extends RuntimeException {

    private CodeMsg cm;
    public GlobalException(CodeMsg cm){
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
