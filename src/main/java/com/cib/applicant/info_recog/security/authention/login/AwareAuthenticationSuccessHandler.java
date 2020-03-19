package com.cib.applicant.info_recog.security.authention.login;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.cib.applicant.info_recog.security.config.WebSecurityConfig;
import com.cib.applicant.info_recog.security.model.TokenFactory;
import com.cib.applicant.info_recog.security.model.UserContext;
import com.cib.applicant.info_recog.security.model.token.AccessToken;
import com.cib.applicant.info_recog.util.WebUtils;


/**
 * 认证成功处理程序
 * @since 2018年4月27日下午10:01:45
 * @author 刘俊杰
 */
@Component
public class AwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final TokenFactory tokenFactory;

    @Autowired
    public AwareAuthenticationSuccessHandler(final TokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserContext userContext = (UserContext) authentication.getPrincipal();

        AccessToken accessToken = tokenFactory.createAccessToken(userContext);
        //往coockie中添加token
          WebUtils.addCookie("token", accessToken.getToken(), response,null);
//        WebUtils.addCookie("refreshToken", refreshToken.getToken(),response,null);
        request.getSession().setAttribute(WebSecurityConfig.TOKEN_HEADER_PARAM, accessToken.getToken());
        response.setHeader(WebSecurityConfig.TOKEN_HEADER_PARAM, accessToken.getToken());
        // request.getRequestDispatcher("/main").forward(request, response);
        response.sendRedirect("/main");
        //clearAuthenticationAttributes(request);
    }

    /**
     * Removes temporary authentication-related data which may have been stored
     * in the session during the authentication process..
     */
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
