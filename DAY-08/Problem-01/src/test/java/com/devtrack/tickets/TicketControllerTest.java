package com.devtrack.tickets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateBugReportWhenPayloadIsValid() throws Exception {
        String requestBody = """
                {
                  "type": "BUG",
                  "title": "Login button is broken",
                  "description": "The login button does not respond on click.",
                  "severity": 4,
                  "stepsToReproduce": "Open app, click login."
                }
                """;

        mockMvc.perform(post("/api/v1/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().string("Bug report created successfully"));
    }

    @Test
    void shouldReturnFieldErrorsWhenValidationFails() throws Exception {
        String requestBody = """
                {
                  "type": "BUG",
                  "title": "Bug with invalid severity",
                  "description": "Severity exceeds the allowed limit.",
                  "severity": 9,
                  "stepsToReproduce": "Create a bad payload."
                }
                """;

        mockMvc.perform(post("/api/v1/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.severity").value("Severity must be between 1 and 5"));
    }

    @Test
    void shouldReturn422WhenTicketTypeIsUnsupported() throws Exception {
        String requestBody = """
                {
                  "type": "HOTFIX",
                  "title": "Critical patch request",
                  "description": "Ship a one-off production patch immediately."
                }
                """;

        mockMvc.perform(post("/api/v1/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error")
                        .value("Ticket type 'HOTFIX' is not supported. Supported types are BUG and FEATURE."));
    }
}
