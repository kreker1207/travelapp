package com.project.trav.controller;

import com.project.trav.service.security.JwtTokenProvider;
import com.project.trav.model.entity.User;
import com.project.trav.repository.UserRepository;
import com.project.trav.exeption.JwtAuthenticationException;
import com.project.trav.model.dto.AuthenticationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;

  @PostMapping("/login")
  public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequestDto requestDto) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(requestDto.getLogin(), requestDto.getPassword()));
      User user = userRepository.findByLogin(requestDto.getLogin())
          .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
      String token = jwtTokenProvider.createToken(requestDto.getLogin(), user.getRole().name());
      Map<Object, Object> response = new HashMap<>();
      response.put("login", requestDto.getLogin());
      response.put("token", token);
      return ResponseEntity.ok(response);
    } catch (JwtAuthenticationException e) {
      return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
    }
  }

  @PostMapping("/logout")
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
    securityContextLogoutHandler.logout(request, response, null);
  }
}
