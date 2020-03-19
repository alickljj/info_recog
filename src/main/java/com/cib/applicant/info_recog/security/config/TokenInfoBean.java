package com.cib.applicant.info_recog.security.config;

/**
 * 
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 
 * @since 2018年4月28日上午11:13:47
 * @author 刘俊杰
 */
@RefreshScope
@Configuration
public class TokenInfoBean {
    /**
     * {@link com.IToken.security.model.token.Token} token的过期时间
     */
	@Value("${token.expirationTime}")
    private Integer expirationTime;

    /**
     * 发行人
     */
	@Value("${token.issuer}")
    private String issuer;

    /**
     * 使用的签名KEY {@link com.IToken.security.model.token.Token}.
     */
	@Value("${token.signingKey}")
    private String signingKey;

    /**
     * {@link com.IToken.security.model.token.Token} 刷新过期时间
     */
	@Value("${token.refreshExpTime}")
    private Integer refreshExpTime;
    @Bean     //声明其为Bean实例  
    @Primary 
    @RefreshScope   
    public TokenProperties getTokenProperties(){
    	TokenProperties tokenProperties = new TokenProperties();
    	tokenProperties.setExpirationTime(expirationTime);
    	tokenProperties.setIssuer(issuer);
    	tokenProperties.setRefreshExpTime(refreshExpTime);
    	tokenProperties.setSigningKey(signingKey);
		return tokenProperties;
    	
    }
}
