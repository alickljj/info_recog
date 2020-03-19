package com.cib.applicant.info_recog.security.exception;



import org.springframework.security.core.AuthenticationException;

import com.cib.applicant.info_recog.security.model.token.IToken;


/**
 * 过期的Token
 * @since 2018年4月27日下午10:07:13
 * @author 刘俊杰
 */
public class ExpiredTokenException extends AuthenticationException {
    private static final long serialVersionUID = -5959543783324224864L;
    
    private IToken token;

    public ExpiredTokenException(String msg) {
        super(msg);
    }

    public ExpiredTokenException(IToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }
}
