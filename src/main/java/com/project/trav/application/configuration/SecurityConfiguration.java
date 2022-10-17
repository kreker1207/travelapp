package com.project.trav.application.configuration;

import com.project.trav.application.security.JwtConfiguration;
import com.project.trav.domain.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
public class SecurityConfiguration {
    public static final String USERS = "/users**";
    public static final String TICKETS = "/tickets**";
    public static final String RACES = "/races**";
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
                .authorizeHttpRequests(authorize->authorize
                        .antMatchers(HttpMethod.POST,USERS).permitAll()
                        .antMatchers(HttpMethod.GET,USERS).hasRole(Role.ADMIN.name())
                        .antMatchers(HttpMethod.DELETE,USERS).hasAnyRole(Role.USER.name(),Role.ADMIN.name())
                        .antMatchers(HttpMethod.PUT,USERS).hasAnyRole(Role.USER.name(),Role.ADMIN.name())
                        .antMatchers(HttpMethod.DELETE,USERS,TICKETS,RACES).hasRole(Role.ADMIN.name())
                        .antMatchers(HttpMethod.GET,TICKETS,RACES).hasAnyRole(Role.USER.name(),Role.ADMIN.name())
                        .antMatchers(HttpMethod.POST,TICKETS).hasAnyRole(Role.USER.name(),Role.ADMIN.name())
                        .antMatchers(HttpMethod.PUT,TICKETS,RACES).hasRole(Role.ADMIN.name())
                        .antMatchers("/api/v1/auth/login").permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .apply(jwtConfiguration)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic(withDefaults());
        return http.build();
    }
}
