package com.cloudvault.resourceserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ResourceServerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAllowPublicEndpointWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/api/v1/public/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("public"));
    }

    @Test
    void shouldRequireAuthenticationForNonPublicEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/profile"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldAllowAuthenticatedAccessForGeneralProtectedEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/profile")
                        .with(jwt().jwt(jwt -> jwt.subject("athena"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("athena"));
    }

    @Test
    void shouldRequireAdminScopeForAdminEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/admin/audit")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_admin"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("admin"));
    }

    @Test
    void shouldRejectAdminEndpointWhenAdminScopeIsMissing() throws Exception {
        mockMvc.perform(get("/api/v1/admin/audit")
                        .with(jwt()))
                .andExpect(status().isForbidden());
    }
}
