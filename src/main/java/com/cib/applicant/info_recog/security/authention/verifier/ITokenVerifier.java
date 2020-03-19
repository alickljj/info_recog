package com.cib.applicant.info_recog.security.authention.verifier;



/**
 * token认证接口
 * @since 2018年4月27日下午10:04:02
 * @author 刘俊杰
 */
public interface ITokenVerifier {
    boolean verify(String jti);
}
