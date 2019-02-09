package com.wei.interceptor;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;



        import java.lang.annotation.Retention;
        import java.lang.annotation.Target;

        import static java.lang.annotation.ElementType.METHOD;
        import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Administrator on 2/8.
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface AccessLimit {

    int  seconds();
    boolean needLogin() default true;
    int maxCount();
}