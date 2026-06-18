package com.secureauth.onboarding.validator;

import com.secureauth.onboarding.annotation.PasswordsMatch;
import com.secureauth.onboarding.dto.RegistrationRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, RegistrationRequestDTO> {

    @Override
    public boolean isValid(RegistrationRequestDTO value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (value.getPassword() == null || value.getConfirmPassword() == null) {
            return true;
        }

        return value.getPassword().equals(value.getConfirmPassword());
    }
}
