package com.farfocle.password_validator;

import java.util.List;

public class ValidationResult extends BaseValidationResult<ErrorDetails>{

    public ValidationResult(List<ErrorDetails> errors) {
        super(errors);
    }
}
