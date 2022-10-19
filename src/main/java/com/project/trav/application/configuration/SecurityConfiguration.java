package com.project.trav.application.configuration;

import com.project.trav.application.security.JwtConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    public static final String USERS = "/users**";
    private final JwtConfiguration jwtConfiguration;
    private final AuthenticationConfiguration configuration;
    @Bean
    public AuthenticationManager authenticationManagerBean() throws  Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    protected PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder(12);}
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(authorize->authorize
                        .antMatchers("/").permitAll()
                        .antMatchers("/api/v1/auth/login").permitAll()
                        .antMatchers(HttpMethod.POST,USERS).permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .apply(jwtConfiguration)
                .and()
                .httpBasic(withDefaults());
        return http.build();
    }
}
