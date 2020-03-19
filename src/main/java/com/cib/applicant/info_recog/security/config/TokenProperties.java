package com.cib.applicant.info_recog.security.config;


import org.springframework.context.annotation.Configuration;

/**
 * token生成策略
 * @since 2018年4月27日下午10:06:00
 * @author 刘俊杰
 */
@Configuration
public class TokenProperties {
    /**
     * {@link com.IToken.security.model.token.Token} token的过期时间
     */
    private Integer expirationTime;

    /**
     * 发行人
     */
    private String issuer;

    /**
     * 使用的签名KEY {@link com.IToken.security.model.token.Token}.
     */
    private String signingKey;

    /**
     * {@link com.IToken.security.model.token.Token} 刷新过期时间
     */
    private Integer refreshExpTime;

    public Integer getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Integer expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSigningKey() {
        return signingKey;
    }

    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }

    public Integer getRefreshExpTime() {
        return refreshExpTime;
    }

    public void setRefreshExpTime(Integer refreshExpTime) {
        this.refreshExpTime = refreshExpTime;
    }
}
