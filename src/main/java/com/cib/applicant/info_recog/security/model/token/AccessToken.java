package com.cib.applicant.info_recog.security.model.token;



import io.jsonwebtoken.Claims;

/**
 * 验证Token
 * @since 2018年4月27日下午10:07:32
 * @author 刘俊杰
 */
public final class AccessToken implements IToken {
    private final String rawToken;
    private Claims claims;

    public AccessToken(final String token, Claims claims) {
        this.rawToken = token;
        this.claims = claims;
    }

    public String getToken() {
        return this.rawToken;
    }

    public Claims getClaims() {
        return claims;
    }
}
