package com.farfocle.password_validator;

import java.util.List;

public interface IPasswordValidator {
    ValidationResult validate(PasswordData passwordData);
    SimpleValidationResult validateErrors(PasswordData passwordData);
    boolean validateSimple(PasswordData passwordData);

    List<PasswordError> getAvailableErrors();
}
