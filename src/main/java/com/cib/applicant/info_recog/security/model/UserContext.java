package com.cib.applicant.info_recog.security.model;


import org.apache.commons.lang3.StringUtils;

/**
 * 用户权限信息
 * @since 2018年4月27日下午10:10:01
 * @author 刘俊杰
 */
public class UserContext {
    private final String username;
    private final String userid;

    private UserContext(String username, String userid) {
        this.username = username;
        this.userid = userid;
    }

    public static UserContext create(String username, String userid) {
        if (StringUtils.isBlank(username)) throw new IllegalArgumentException("Username is blank: " + username);
        return new UserContext(username, userid);
    }

    public String getUsername() {
        return username;
    }

    public String getUserId() {
        return userid;
    }
}
