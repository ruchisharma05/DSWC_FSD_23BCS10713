package com.ledgerx.batch.validator;

import com.ledgerx.batch.annotation.SupportedCurrency;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class SupportedCurrencyValidator implements ConstraintValidator<SupportedCurrency, String> {

    private static final Set<String> SUPPORTED_CURRENCIES = Set.of("USD", "EUR", "GBP");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && SUPPORTED_CURRENCIES.contains(value);
    }
}
