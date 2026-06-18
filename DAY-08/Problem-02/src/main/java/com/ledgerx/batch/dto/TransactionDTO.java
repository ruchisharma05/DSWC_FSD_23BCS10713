package com.ledgerx.batch.dto;

import com.ledgerx.batch.annotation.SupportedCurrency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TransactionDTO {

    @NotNull(message = "Amount is required")
    @Positive(message = "Must be greater than 0")
    private BigDecimal amount;

    @SupportedCurrency
    private String currency;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
