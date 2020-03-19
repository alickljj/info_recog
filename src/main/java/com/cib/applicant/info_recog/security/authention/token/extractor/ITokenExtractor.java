package com.cib.applicant.info_recog.security.authention.token.extractor;




/**
 * 接口 应该返回原Base-64编码
 * 表示Token
 * @since 2018年4月27日下午10:03:39
 * @author 刘俊杰
 */
public interface ITokenExtractor {
    String extract(String payload);
}
