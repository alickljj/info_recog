package com.cib.applicant.info_recog.security.authention.login;



import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 客户端登录认证模型
 * @since 2018年4月27日下午10:03:05
 * @author 刘俊杰
 */
public class LoginRequest {
    private String username;
    private String password;

    @JsonCreator
    public LoginRequest(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
