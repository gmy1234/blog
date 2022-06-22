package com.gmy.blog.config;


import com.gmy.blog.filter.JwtAuthenticationTokenFilter;
import com.gmy.blog.handler.AccessDeniedHandlerImpl;
import com.gmy.blog.handler.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author gmydl
 * @title: WebSecurityConfig
 * @projectName blog-api
 * @description: spring security 配置类
 * @date 2022/6/4 20:33
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 密码加密
     *
     * @return {@link PasswordEncoder} 加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @Autowired
    private AuthenticationSuccessHandler AuthenticationSuccessHandlerImpl;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    /**
     * 注册bean sessionRegistry
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    /**
     * 解决session失效后 sessionRegistry中session没有同步失效的问题，
     * 启用并发session控制，首先需要在配置中增加下面监听器
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                // 关闭csrf
                .csrf().disable()
                // 不通过 Session 获取 SecurityContext，前后端分离，session基本不用
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 请求认证：
                .authorizeRequests()
                .antMatchers("/user/login").anonymous() // anonymous() 匿名状态可访问，已经登陆状态不能访问。
                .antMatchers("/user/code").anonymous() // permitAll() 无论是否登陆，都可以访问。
                .antMatchers("/user/resetPassword").anonymous()
                .antMatchers("/wallpaper/**").permitAll() // 壁纸开放
                .antMatchers("/blog/**").permitAll() // 博客网站模块
                .antMatchers("/article/**").permitAll() // 文章模块
                .antMatchers("/comment/getComments").permitAll() // 评论模块
                .antMatchers("/comment/**/replies").permitAll()
                .antMatchers("/admin/user/login").anonymous() // 设置后台登陆过滤
                .antMatchers("/admin/user/info").permitAll()
                .antMatchers("/admin/upload/articles/images").permitAll() // 放行上传文章封面，特殊
                .antMatchers("/admin/album/uploadCover").permitAll()
                .antMatchers("/admin/**/**").hasAnyAuthority("admin")
                .anyRequest().authenticated();

        // 添加过滤器，在指定的过滤器之前添加。p1: 过滤器，p2: 指定过滤器的字节码。
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);


        // 配置异常处理器和 Session
        http.exceptionHandling()
                // 登陆失败处理
                .authenticationEntryPoint(authenticationEntryPoint)
                // 权限不足处理（授权失败处理）
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .sessionManagement()
                .maximumSessions(20)
                .sessionRegistry(sessionRegistry());
    }

}
