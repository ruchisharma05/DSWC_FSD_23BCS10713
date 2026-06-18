package com.secureauth.onboarding;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRegisterUserWithoutExposingSensitiveFields() throws Exception {
        String requestBody = """
                {
                  "username": "sarah.connor",
                  "email": "sarah.connor@secureauth.com",
                  "password": "StrongPass123",
                  "confirmPassword": "StrongPass123"
                }
                """;

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("sarah.connor"))
                .andExpect(jsonPath("$.email").value("sarah.connor@secureauth.com"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.id").doesNotExist());
    }

    @Test
    void shouldReturnFieldAndGlobalErrorsInOneResponse() throws Exception {
        String requestBody = """
                {
                  "username": "neo",
                  "email": "not-an-email",
                  "password": "Matrix123",
                  "confirmPassword": "Mismatch123"
                }
                """;

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("Email must be a valid email address"))
                .andExpect(jsonPath("$.passwordsMatch").value("Password and confirm password must match"));
    }
}
