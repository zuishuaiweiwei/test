package com.wei.exception;
import com.wei.exception.GlobalException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;




        import com.wei.result.CodeMsg;
        import com.wei.result.Result;
        import org.springframework.validation.BindException;
        import org.springframework.validation.ObjectError;
        import org.springframework.web.bind.annotation.ControllerAdvice;
        import org.springframework.web.bind.annotation.ExceptionHandler;
        import org.springframework.web.bind.annotation.ResponseBody;

        import javax.servlet.http.HttpServletRequest;
        import java.util.List;


/**
 * 全局异常拦截器
 *
 * @author 为为
 * @create 1/26
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandle(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException) e;
            return Result.error(ex.getCm());
        } else if (e instanceof BindException) {
            BindException ex = (BindException) e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String defaultMessage = error.getDefaultMessage();
            return Result.error(CodeMsg.Bind_ERROR.fillArgs(defaultMessage));
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
