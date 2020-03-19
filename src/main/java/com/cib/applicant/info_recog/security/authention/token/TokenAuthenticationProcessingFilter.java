package com.cib.applicant.info_recog.security.authention.token;



import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.cib.applicant.info_recog.security.authention.AuthenticationToken;
import com.cib.applicant.info_recog.security.authention.token.extractor.ITokenExtractor;
import com.cib.applicant.info_recog.security.config.WebSecurityConfig;
import com.cib.applicant.info_recog.security.model.token.RawAccessToken;
import com.cib.applicant.info_recog.util.WebUtils;

/**
 * 执行Token的验证
 * 
 * @since 2018年4月27日下午10:04:57
 * @author 刘俊杰
 */
public class TokenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
	private final AuthenticationFailureHandler failureHandler;
	private final ITokenExtractor tokenExtractor;

	@Autowired
	public TokenAuthenticationProcessingFilter(AuthenticationFailureHandler failureHandler,
                                               ITokenExtractor tokenExtractor, RequestMatcher matcher) {
		super(matcher);
		this.failureHandler = failureHandler;
		this.tokenExtractor = tokenExtractor;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		String tokenPayload = "";
		if(WebUtils.isAjax(request)){
			tokenPayload = request.getHeader(WebSecurityConfig.TOKEN_HEADER_PARAM);
		}
		if (tokenPayload == "" || tokenPayload == null) {
			tokenPayload = WebUtils.getCookieByName(request, "token");
		}
		RawAccessToken token = new RawAccessToken(tokenExtractor.extract(tokenPayload));
		return getAuthenticationManager().authenticate(new AuthenticationToken(token));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authResult);
		SecurityContextHolder.setContext(context);
		chain.doFilter(request, response);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		SecurityContextHolder.clearContext();
		failureHandler.onAuthenticationFailure(request, response, failed);
	}
}
