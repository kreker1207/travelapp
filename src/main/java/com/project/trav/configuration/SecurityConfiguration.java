package com.project.trav.configuration;

import com.project.trav.service.security.JwtConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
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

import static io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER;
import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@SecurityScheme(name = SecurityConfiguration.SECURITY_CONFIG_NAME, in = HEADER, type = HTTP, scheme = "bearer", bearerFormat = "JWT")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

  private final JwtConfiguration jwtConfiguration;
  private final AuthenticationConfiguration configuration;
  public static final String SECURITY_CONFIG_NAME = "App Bearer token";

  private static final String[] SWAGGER_WHITELIST = {
      "/swagger-ui/**",
      "/swagger-ui.html",
      "/swagger-ui.html/**",
      "/configuration/ui",
      "/swagger-resources/**",
      "/v3/api-docs/**",
      "/configuration/**",
      "/swagger-resources/**",
      "/webjars/**"
  };

  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeHttpRequests(authorize -> authorize
            .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
            .antMatchers(HttpMethod.POST, "/v1/users").permitAll()
            .antMatchers(SWAGGER_WHITELIST).permitAll()
            .antMatchers(HttpMethod.POST, "/v1/auth/login").permitAll()
            .anyRequest()
            .authenticated()
        )
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .apply(jwtConfiguration);
    return http.build();
  }
}
