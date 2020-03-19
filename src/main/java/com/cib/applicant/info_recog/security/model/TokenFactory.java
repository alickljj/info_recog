package com.cib.applicant.info_recog.security.model;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cib.applicant.info_recog.security.config.TokenInfoBean;
import com.cib.applicant.info_recog.security.config.TokenProperties;
import com.cib.applicant.info_recog.security.model.token.AccessToken;
import com.cib.applicant.info_recog.security.model.token.IToken;
import com.cib.applicant.info_recog.security.model.token.Scopes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


/**
 * Token创建工厂类
 * @since 2018年4月27日下午10:08:54
 * @author 刘俊杰
 */
@Component
public class TokenFactory {

    private final TokenProperties properties;

    @Autowired
    public TokenFactory(TokenInfoBean properties) {
        this.properties = properties.getTokenProperties();
    }

    /**
     * 利用JJWT 生成 Token
     *
     * @param context
     * @return
     */
    public AccessToken createAccessToken(UserContext context) {
        Optional.ofNullable(context.getUsername()).orElseThrow(() -> new IllegalArgumentException("Cannot create Token without username"));
        Optional.ofNullable(context.getUserId()).orElseThrow(() -> new IllegalArgumentException("User doesn't have any privileges"));
        Claims claims = Jwts.claims().setSubject(context.getUsername());
        claims.put("scopes", context.getUserId());
        LocalDateTime currentTime = LocalDateTime.now();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(properties.getIssuer())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(properties.getExpirationTime())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, properties.getSigningKey())
                .compact();
        return new AccessToken(token, claims);
    }

    /**
     * 生成 刷新 RefreshToken
     *
     * @param userContext
     * @return
     */
    public IToken createRefreshToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getUsername())) {
            throw new IllegalArgumentException("Cannot create Token without username");
        }
        LocalDateTime currentTime = LocalDateTime.now();
        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("scopes", Collections.singletonList(Scopes.REFRESH_TOKEN.getUser()));
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(properties.getIssuer())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(properties.getRefreshExpTime())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, properties.getSigningKey())
                .compact();

        return new AccessToken(token, claims);
    }
}
