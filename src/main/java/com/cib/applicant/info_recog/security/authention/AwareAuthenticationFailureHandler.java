package com.cib.applicant.info_recog.security.authention;


import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.cib.applicant.info_recog.security.exception.AuthMethodNotSupportedException;
import com.cib.applicant.info_recog.security.exception.ExpiredTokenException;
import com.cib.applicant.info_recog.util.WebUtils;


/**
 * 认证失败处理程序
 * @since 2018年4月27日下午10:01:45
 * @author 刘俊杰
 */
@Component
public class AwareAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        String message = "";
        if (e instanceof BadCredentialsException) {
        	message = "用户名或密码错误";
        	this.redreictToLogin(request,response, message);
        } else if (e instanceof ExpiredTokenException) {
           message = "登陆超时";
           this.timeOut(request,response, message);
        } else if (e instanceof AuthMethodNotSupportedException || e instanceof AuthenticationServiceException) {
        	message = e.getMessage();
        	this.redreictToLogin(request,response, message);
        }
        
    }
    private void timeOut(HttpServletRequest req,HttpServletResponse response,String message) throws IOException{
    	message = URLEncoder.encode(message,"utf-8");
    	if (WebUtils.isAjax(req)){
        	response.sendError(209);
        }else{
        	response.sendRedirect("/login_error?message=" + message);
        }
    	return;
        //response.sendRedirect("/login_error?message=" + message);
        
    }
    private void redreictToLogin(HttpServletRequest req,HttpServletResponse response,String message) throws IOException{
        message = URLEncoder.encode(message,"utf-8");
    	//
        if (WebUtils.isAjax(req)){
        	response.sendError(209);
        }else{
        	response.sendRedirect("/login_error?message=" + message);
        }
        return;
    }
}
