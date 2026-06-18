package com.gatekeeper.filter.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class GatewayController {

    @GetMapping("/public/ping")
    public Map<String, String> ping() {
        return Map.of("status", "public-ok");
    }

    @GetMapping("/private/whoami")
    public Map<String, Object> whoAmI(Authentication authentication) {
        return Map.of(
                "username", authentication.getName(),
                "authorities", authentication.getAuthorities().stream().map(Object::toString).toList()
        );
    }
}
