package com.enterpriseassistant.infrastructure.security.jwt.config;

import com.enterpriseassistant.infrastructure.security.jwt.JwtAuthTokenFilter;
import com.enterpriseassistant.infrastructure.security.jwt.LoginUserDetailsService;
import com.enterpriseassistant.user.domain.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.Clock;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthTokenFilter jwtAuthTokenFilter;

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService(UserFacade userFacade) {
        return new LoginUserDetailsService(userFacade);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and()
                .csrf().disable();
        httpSecurity.authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/console/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/api-docs").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable()
                .and().httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .and()
                .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

}
