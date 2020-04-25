package com.jeramtough.randl2.bean.registereduser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <pre>
 * Created on 2020/1/30 10:44
 * by @author JeramTough
 * </pre>
 */
@ApiModel("通过验证码登录参数")
public class LoginByVerificationCodeCredentials {

    @NotNull(message = "{'code':1002,'placeholders':['登录凭证']}")
    @Pattern(
            regexp = "(^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$)|(^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$)",
            message = "{'code':668,'placeholders':['登录凭证','手机号码或者邮箱地址']}")
    @ApiParam(value = "手机号、邮箱地址", required = true)
    private String credential;

    @NotNull(message = "{'code':667,'placeholders':['登录失败','验证码']}")
    @ApiModelProperty(value = "验证码", example = "108764", required = true)
    @Pattern(regexp = "^[0-9]{6}$", message = "{'code':668,'placeholders':['验证码'," +
            "'6位长度正整数']}")
    private String verificationCode;

    public LoginByVerificationCodeCredentials() {
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}