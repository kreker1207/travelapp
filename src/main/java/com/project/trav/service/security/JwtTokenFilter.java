package com.project.trav.service.security;

import com.project.trav.exeption.JwtAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    String tokenProvided = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
    try {

      if (tokenProvided != null && jwtTokenProvider.validateToken(validateBearer(tokenProvided))) {
        Authentication authentication = jwtTokenProvider.getAuthentication(validateBearer(tokenProvided));
        if (authentication != null) {
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    } catch (JwtAuthenticationException e) {
      SecurityContextHolder.clearContext();
      ((HttpServletResponse) servletResponse).sendError(e.getHttpStatus().value());
      throw new JwtAuthenticationException("Jwt token is expired or invalid");
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }
  private String validateBearer(String token){
    if (token.startsWith("Bearer ")){
      String[] parts = token.split("Bearer ") ;
      String realToken = parts[1];
      return realToken;
    }
    else return token;
  }
}
