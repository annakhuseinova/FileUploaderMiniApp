package com.annakhuseinova.FileUploaderApp.config;


import com.annakhuseinova.FileUploaderApp.security.CustomUserDetailsService;
import com.annakhuseinova.FileUploaderApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private UserService userService;

    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    public void setCustomUserDetailsService(CustomUserDetailsService customUserDetailsService){
        this.customUserDetailsService = customUserDetailsService;
    }

    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    public void setCustomAuthenticationSuccessHandler(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/registration")
                .permitAll().antMatchers("/signin").permitAll().antMatchers("/main").authenticated().
                antMatchers("/downloadfiles/**","/deletefiles/**").authenticated().
                and().formLogin().loginPage("/signin").loginProcessingUrl("/signin").successHandler(customAuthenticationSuccessHandler).
                and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and().rememberMe().tokenValiditySeconds(2592000).userDetailsService(customUserDetailsService);


    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {

        return new BCryptPasswordEncoder(8);
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(getBCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        return daoAuthenticationProvider;
    }




}

