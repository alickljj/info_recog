package com.cib.applicant.info_recog.security.authention.verifier;



import org.springframework.stereotype.Component;

/**
 * Token验证过滤器
 * @since 2018年4月27日下午10:03:53
 * @author 刘俊杰
 */
@Component
public class BloomFilterTokenVerifier implements ITokenVerifier {
    @Override
    public boolean verify(String jti) {
        return true;
    }
}
