package com.cib.applicant.info_recog.security.exception;



import org.springframework.security.authentication.AuthenticationServiceException;

/**
 * 不支持的方法验证 / GET != POST
 * @since 2018年4月27日下午10:07:04
 * @author 刘俊杰
 */
public class AuthMethodNotSupportedException extends AuthenticationServiceException {
    private static final long serialVersionUID = 3705043083010304496L;

    public AuthMethodNotSupportedException(String msg) {
        super(msg);
    }
}
