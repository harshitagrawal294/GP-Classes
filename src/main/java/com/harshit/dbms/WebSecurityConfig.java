package com.harshit.dbms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    
    	http.csrf().disable();
    	http
        .authorizeRequests()
            .antMatchers("/", "/register/student/").permitAll()
            .and()
        .formLogin()
            .loginPage("/login")
            .permitAll()
            .defaultSuccessUrl("/welcome",true)
            .and()
        .logout()
            .permitAll();

//        
//    	http.csrf().disable();
        http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll();
//
        http.authorizeRequests().antMatchers("/student", "/student/**").access("hasAnyAuthority('student')");

        http.authorizeRequests().antMatchers("/faculty", "/faculty/**").access("hasAnyAuthority('faculty')");
        
        http.authorizeRequests().antMatchers("/staff", "/staff/**").access("hasAnyAuthority('staff')");
        
        http.authorizeRequests().antMatchers("/admin", "/admin/**").access("hasAnyAuthority('admin')");

        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
//        
//
//        http.authorizeRequests().and().formLogin().loginProcessingUrl("/j_spring_security_check")
//                .defaultSuccessUrl("/welcome").failureUrl("/login?error=true").usernameParameter("username")
//                .passwordParameter("password").and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
//
    }
}
