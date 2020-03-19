package com.cib.applicant.info_recog.security.model.token;

/**
 * 角色枚举
 * @since 2018年4月27日下午10:09:15
 * @author 刘俊杰
 */
public enum Scopes {
    REFRESH_TOKEN;

    public String getUser() {
        return "user_" + this.name();
    }
}
