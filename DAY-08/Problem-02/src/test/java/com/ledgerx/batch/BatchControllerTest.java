package com.ledgerx.batch;

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
class BatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAcceptValidBatch() throws Exception {
        String requestBody = """
                {
                  "batchId": "BATCH-1001",
                  "transactions": [
                    {
                      "amount": 250.75,
                      "currency": "USD"
                    },
                    {
                      "amount": 99.99,
                      "currency": "EUR"
                    }
                  ]
                }
                """;

        mockMvc.perform(post("/api/v1/batches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Batch accepted for processing"));
    }

    @Test
    void shouldReportArrayIndexesForNestedValidationErrors() throws Exception {
        String requestBody = """
                {
                  "batchId": "BATCH-1002",
                  "transactions": [
                    {
                      "amount": -50.00,
                      "currency": "USD"
                    },
                    {
                      "amount": 120.00,
                      "currency": "INR"
                    }
                  ]
                }
                """;

        mockMvc.perform(post("/api/v1/batches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['transactions[0].amount']").value("Must be greater than 0"))
                .andExpect(jsonPath("$.['transactions[1].currency']").value("Currency must be one of USD, EUR, GBP"));
    }
}
