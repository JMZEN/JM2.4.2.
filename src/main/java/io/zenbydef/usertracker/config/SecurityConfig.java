package io.zenbydef.usertracker.config;

import io.zenbydef.usertracker.handlers.AuthSuccessHandler;
import io.zenbydef.usertracker.service.securitydetailuserservice.SecurityDetailUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final SecurityDetailUserService securityDetailUserService;

    public SecurityConfig(SecurityDetailUserService securityDetailUserService) {
        this.securityDetailUserService = securityDetailUserService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(securityDetailUserService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthSuccessHandler authSuccessHandler() {
        return new AuthSuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize ->
                        authorize
                                .antMatchers("/signup", "/user/register")
                                .permitAll())
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin(loginConfigurer ->
                        loginConfigurer
                                .loginPage("/login")
                                .permitAll()
                                .loginProcessingUrl("/doLogin")
                                .successHandler(authSuccessHandler()))
                .logout(logoutConfigurer ->
                        logoutConfigurer
                                .permitAll()
                                .logoutUrl("/logout"))
                .csrf().disable();
    }
}

