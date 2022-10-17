package com.project.trav.domain.client;

import com.project.trav.domain.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "userAuthorizingClient",url = "http://localhost:8080/authorization-service")
public interface AuthorizingClient {
    @GetMapping
    public User getUserAuth();
}
