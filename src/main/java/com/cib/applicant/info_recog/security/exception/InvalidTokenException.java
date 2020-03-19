package com.cib.applicant.info_recog.security.exception;



/**
 * 无效的Token
 * @since 2018年4月27日下午10:07:21
 * @author 刘俊杰
 */
public class InvalidTokenException extends RuntimeException {

    private static final long serialVersionUID = -294671188037098603L;

    public InvalidTokenException(String msg) {
        super(msg);
    }

    public InvalidTokenException(String msg, Throwable t) {
        super(msg, t);
    }
}
