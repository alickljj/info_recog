package com.cib.applicant.info_recog.security.authention.token;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.cib.applicant.info_recog.security.authention.AuthenticationToken;
import com.cib.applicant.info_recog.security.config.TokenInfoBean;
import com.cib.applicant.info_recog.security.config.TokenProperties;
import com.cib.applicant.info_recog.security.model.UserContext;
import com.cib.applicant.info_recog.security.model.token.RawAccessToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;



/**
 * 身份验证的实例
 * @since 2018年4月27日下午10:05:24
 * @author 刘俊杰
 */
@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

	private Logger logger = LoggerFactory.getLogger(TokenAuthenticationProvider.class);
	private final TokenProperties tokenProperties;

	@Autowired
	public TokenAuthenticationProvider(TokenInfoBean tokenProperties) {
		this.tokenProperties = tokenProperties.getTokenProperties();
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		RawAccessToken rawAccessToken = (RawAccessToken) authentication.getCredentials();
		long startTime = System.currentTimeMillis();
		Jws<Claims> jwsClaims = rawAccessToken.parseClaims(tokenProperties.getSigningKey());
		logger.debug("[验证Token消耗时间] - [{}]", (System.currentTimeMillis() - startTime));
		String subject = jwsClaims.getBody().getSubject();
		String userid = jwsClaims.getBody().get("scopes", String.class);
//		List<GrantedAuthority> authorities = scopes.stream().map(SimpleGrantedAuthority::new).collect(toList());
		UserContext context = UserContext.create(subject, userid);
		return new AuthenticationToken(context, null);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (AuthenticationToken.class.isAssignableFrom(authentication));
	}
}
