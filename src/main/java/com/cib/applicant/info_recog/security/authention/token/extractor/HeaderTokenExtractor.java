package com.cib.applicant.info_recog.security.authention.token.extractor;



import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

/**
 * 接口实现获取Token
 * @since 2018年4月27日下午10:03:16
 * @author 刘俊杰
 */
@Component
public class HeaderTokenExtractor implements ITokenExtractor {

    @Override
    public String extract(String header) {
        if (StringUtils.isBlank(header) || "undefined".equals(header)) {
            throw new AuthenticationServiceException("登陆超时");
        }
        return header;
    }
}
