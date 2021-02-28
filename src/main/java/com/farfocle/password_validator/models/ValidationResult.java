package com.farfocle.password_validator.models;

import com.farfocle.password_validator.models.BaseValidationResult;
import com.farfocle.password_validator.models.ErrorDetails;

import java.util.List;

public class ValidationResult extends BaseValidationResult<ErrorDetails> {

    public ValidationResult(List<ErrorDetails> errors) {
        super(errors);
    }
}
