package io.zenbydef.usertracker.config;

import io.zenbydef.usertracker.service.UserService;
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
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }


    @Bean
    public AuthSuccessHandler authSuccessHandler() {
        return new AuthSuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().successHandler(authSuccessHandler());

        http
                .authorizeRequests()
                .antMatchers("/signup", "/user/register").permitAll()
                .anyRequest().authenticated();





//                .formLogin()
//                .loginPage("/login").permitAll()
//                .loginProcessingUrl("/doLogin")

//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/doLogin")
//                .successHandler(authSuccessHandler())
//                .permitAll()



                http.logout().permitAll().logoutUrl("/logout")

                .and()
                .exceptionHandling().accessDeniedPage("/denied");

        http
                .csrf().disable();
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//
//                .formLogin()
//                .loginPage("/login").permitAll()
//                .loginProcessingUrl("/doLogin")
//
//                .and()


//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/user/**").hasRole("USER")
//
//
//                .and()
//                .logout().permitAll().logoutUrl("/logout")

//                .and()

//                .exceptionHandling().accessDeniedPage("/access-denied")

//                .and()

        ;
    }
}
