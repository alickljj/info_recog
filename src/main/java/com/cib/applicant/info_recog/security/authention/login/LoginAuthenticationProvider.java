package com.cib.applicant.info_recog.security.authention.login;

import java.io.File;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import com.cib.applicant.info_recog.security.model.UserContext;
import com.cib.applicant.info_recog.service.FileOperationService;
import com.cib.applicant.info_recog.service.RsaService;
import com.cib.applicant.info_recog.util.exception.ExceptionUtils;

/**
 * 登陆
 * 
 * @since 2018年4月27日下午10:02:23
 * @author 刘俊杰
 */
@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

	final BCryptPasswordEncoder encoder;

	final RsaService resService;

	@Autowired
	public LoginAuthenticationProvider(final BCryptPasswordEncoder encoder, RsaService resService) {
		this.encoder = encoder;
		this.resService = resService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.notNull(authentication, "No authentication data provided");
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		File file = null;
		try {
			file = ResourceUtils.getFile("classpath:userinfo.properties");
		} catch (Exception e) {
			ExceptionUtils.wrapException(e);
		}
		FileOperationService fileService = new FileOperationService();
		Properties prop = fileService.getFilePro(file);
		String filePwd = prop.getProperty("password");
		String fileUser = prop.getProperty("username");
		if ((!StringUtils.isEmpty(filePwd) && !filePwd.equals(password))
				|| (!StringUtils.isEmpty(fileUser) && !fileUser.equals(username))) {
			 throw  new AuthenticationServiceException("Authentication Failed. Username or Password not valid.");
		}

		UserContext userContext = UserContext.create(username, username);

		return new UsernamePasswordAuthenticationToken(userContext, null, null);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
