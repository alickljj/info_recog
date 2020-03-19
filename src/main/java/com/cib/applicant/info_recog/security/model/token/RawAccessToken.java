package com.cib.applicant.info_recog.security.model.token;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

import com.cib.applicant.info_recog.security.exception.ExpiredTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * 分析并且验证Token是否有效
 * @since 2018年4月27日下午10:07:42
 * @author 刘俊杰
 */
public class RawAccessToken implements IToken {

    private static Logger logger = LoggerFactory.getLogger(RawAccessToken.class);

    private String token;

    public RawAccessToken(String token) {
        this.token = token;
    }

    /**
     * 分析并且验证Token是否有效
     *
     * @throws BadCredentialsException 如果验证请求被拒绝，则因为凭据无效 <br> 对于要抛出的异常，它意味着该帐户既不锁定也不禁用。 <br>
     * @throws ExpiredTokenException   过期的Token
     */
    public Jws<Claims> parseClaims(String signingKey) {
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(this.token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            logger.error("Invalid Token", ex);
            throw new BadCredentialsException("Invalid token: ", ex);
        } catch (ExpiredJwtException expiredEx) {
            logger.info("Token is expired", expiredEx);
            throw new ExpiredTokenException(this, "Token expired", expiredEx);
        }
    }

    @Override
    public String getToken() {
        return token;
    }
}
