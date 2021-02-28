package com.farfocle.password_validator;

import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;
import com.farfocle.password_validator.models.PasswordData;
import com.farfocle.password_validator.models.PasswordError;
import com.farfocle.password_validator.models.SimpleValidationResult;
import com.farfocle.password_validator.models.ValidationResult;

import java.util.List;

public interface IPasswordValidator {
    ValidationResult validate(PasswordData passwordData) throws InvalidPasswordDataException;

    SimpleValidationResult validateErrors(PasswordData passwordData) throws InvalidPasswordDataException;

    boolean validateSimple(PasswordData passwordData) throws InvalidPasswordDataException;

    List<PasswordError> getAvailableErrors();
}
