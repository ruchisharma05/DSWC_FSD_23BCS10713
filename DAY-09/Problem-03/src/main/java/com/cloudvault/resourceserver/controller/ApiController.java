package com.cloudvault.resourceserver.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @GetMapping("/public/info")
    public Map<String, String> publicInfo() {
        return Map.of("message", "public");
    }

    @GetMapping("/admin/audit")
    public Map<String, String> adminAudit() {
        return Map.of("message", "admin");
    }

    @GetMapping("/profile")
    public Map<String, String> profile(Authentication authentication) {
        return Map.of("message", authentication.getName());
    }
}
