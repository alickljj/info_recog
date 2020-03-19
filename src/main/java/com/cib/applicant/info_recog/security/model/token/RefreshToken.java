package com.cib.applicant.info_recog.security.model.token;


import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;


/**
 * token刷新
 * @since 2018年4月27日下午10:08:18
 * @author 刘俊杰
 */
public class RefreshToken implements IToken {
    private Jws<Claims> claims;

    private RefreshToken(Jws<Claims> claims) {
        this.claims = claims;
    }

    /**
     * Creates and validates Refresh token
     *
     * @param token
     * @param signingKey
     * @return
     * @throws BadCredentialsException
     */
    public static Optional<RefreshToken> create(RawAccessToken token, String signingKey) {
        Jws<Claims> claims = token.parseClaims(signingKey);
        @SuppressWarnings("unchecked")
        List<String> scopes = claims.getBody().get("scopes", List.class);
        if (scopes == null || scopes.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new RefreshToken(claims));
    }

    @Override
    public String getToken() {
        return null;
    }

    public Jws<Claims> getClaims() {
        return claims;
    }

    public String getJti() {
        return claims.getBody().getId();
    }

    public String getSubject() {
        return claims.getBody().getSubject();
    }
}
