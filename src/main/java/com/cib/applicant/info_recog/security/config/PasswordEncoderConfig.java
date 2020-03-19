package com.cib.applicant.info_recog.security.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码编码
 * @since 2018年4月27日下午10:05:35
 * @author 刘俊杰
 */
@Configuration
public class PasswordEncoderConfig {
    @Bean
    protected BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
