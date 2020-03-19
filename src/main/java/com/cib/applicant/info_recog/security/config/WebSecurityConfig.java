package com.cib.applicant.info_recog.security.config;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cib.applicant.info_recog.security.RestAuthenticationEntryPoint;
import com.cib.applicant.info_recog.security.authention.login.LoginAuthenticationProvider;
import com.cib.applicant.info_recog.security.authention.login.LoginProcessingFilter;
import com.cib.applicant.info_recog.security.authention.token.SkipPathRequestMatcher;
import com.cib.applicant.info_recog.security.authention.token.TokenAuthenticationProcessingFilter;
import com.cib.applicant.info_recog.security.authention.token.TokenAuthenticationProvider;
import com.cib.applicant.info_recog.security.authention.token.extractor.ITokenExtractor;
import com.cib.applicant.info_recog.service.RsaService;

/**
 * 资源拦截配置
 * @since 2018年4月27日下午10:06:18
 * @author 刘俊杰
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String TOKEN_HEADER_PARAM = "X-Authorization";
    private static final String FORM_BASED_LOGIN_ENTRY_POINT = "/login";
    private static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/main**";
    private static final String TOKEN_REFRESH_ENTRY_POINT = "/auth/jwt/refresh_token";

    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final LoginAuthenticationProvider loginAuthenticationProvider;
    private final TokenAuthenticationProvider tokenAuthenticationProvider;
    private final ITokenExtractor tokenExtractor;
    private final RsaService rsaservice;
    @Autowired
    public WebSecurityConfig(RestAuthenticationEntryPoint authenticationEntryPoint, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler, LoginAuthenticationProvider loginAuthenticationProvider, TokenAuthenticationProvider tokenAuthenticationProvider, ITokenExtractor tokenExtractor,RsaService rsaservice) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.loginAuthenticationProvider = loginAuthenticationProvider;
        this.tokenAuthenticationProvider = tokenAuthenticationProvider;
        this.tokenExtractor = tokenExtractor;
        this.rsaservice = rsaservice;
    }

    private LoginProcessingFilter buildLoginProcessingFilter() throws Exception {
        LoginProcessingFilter filter = new LoginProcessingFilter(FORM_BASED_LOGIN_ENTRY_POINT, successHandler, failureHandler,rsaservice);
        filter.setAuthenticationManager(super.authenticationManager());
        return filter;
    }

    private TokenAuthenticationProcessingFilter buildTokenAuthenticationProcessingFilter() throws Exception {
        List<String> list = new ArrayList<String>();
        list.add(TOKEN_BASED_AUTH_ENTRY_POINT);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(list);
        TokenAuthenticationProcessingFilter filter = new TokenAuthenticationProcessingFilter(failureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(super.authenticationManager());
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(loginAuthenticationProvider);
        auth.authenticationProvider(tokenAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 因为使用的是JWT，因此这里可以关闭csrf了
                // 跨域支持
                //.cors().and()
                .exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(FORM_BASED_LOGIN_ENTRY_POINT).permitAll() // 登陆不鉴权
                .antMatchers(TOKEN_REFRESH_ENTRY_POINT).permitAll() // token 刷新不鉴权
                .and()
                .authorizeRequests()
                .antMatchers(TOKEN_BASED_AUTH_ENTRY_POINT).authenticated() // Protected API End-points
                .and()
                .addFilterBefore(buildLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
