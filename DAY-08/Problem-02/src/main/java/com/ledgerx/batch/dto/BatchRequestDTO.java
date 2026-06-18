package com.ledgerx.batch.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class BatchRequestDTO {

    @NotBlank(message = "Batch ID must not be blank")
    private String batchId;

    @Valid
    @NotEmpty(message = "Transactions must not be empty")
    private List<TransactionDTO> transactions;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }
}
