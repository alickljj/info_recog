package com.cib.applicant.info_recog.security.authention.login;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.cib.applicant.info_recog.security.exception.AuthMethodNotSupportedException;
import com.cib.applicant.info_recog.service.RsaService;

/**
 * 登录处理过滤器
 * 
 * @since 2018年4月27日下午10:02:49
 * @author 刘俊杰
 */
public class LoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

	private final AuthenticationSuccessHandler successHandler;
	private final AuthenticationFailureHandler failureHandler;
	private final RsaService rsaservice;

	public LoginProcessingFilter(String defaultProcessUrl, AuthenticationSuccessHandler successHandler,
			AuthenticationFailureHandler failureHandler, RsaService rsaservice) {
		super(defaultProcessUrl);
		this.successHandler = successHandler;
		this.failureHandler = failureHandler;
		this.rsaservice = rsaservice;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		if(request.getParameter("username") != null && request.getParameter("password") != null) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			//password = rsaservice.decrypt(password);
			LoginRequest loginRequest = new LoginRequest(username, password);
			if (StringUtils.isBlank(loginRequest.getUsername()) || StringUtils.isBlank(loginRequest.getPassword())) {
				throw new AuthenticationServiceException("Username or Password not provided");
			}
			// 通过用户名密码生成认证token
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					loginRequest.getUsername(), loginRequest.getPassword());
			return this.getAuthenticationManager().authenticate(token);

		} else {
			throw new AuthMethodNotSupportedException("User info is null");
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		successHandler.onAuthenticationSuccess(request, response, authResult);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		SecurityContextHolder.clearContext();
		failureHandler.onAuthenticationFailure(request, response, failed);
	}
}
