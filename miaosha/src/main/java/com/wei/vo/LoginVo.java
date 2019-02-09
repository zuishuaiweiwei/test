package com.wei.vo;

import com.wei.validator.IsMobile;

import javax.validation.constraints.NotNull;


/**
 * @author 为为
 * @create 1/25
 */
public class LoginVo {

    @NotNull
    @IsMobile
    private String mobile;
    @NotNull
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
