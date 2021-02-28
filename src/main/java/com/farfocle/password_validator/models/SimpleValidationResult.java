package com.farfocle.password_validator.models;

import com.farfocle.password_validator.models.BaseValidationResult;
import com.farfocle.password_validator.models.PasswordError;

import java.util.List;

public class SimpleValidationResult extends BaseValidationResult<PasswordError> {

    public SimpleValidationResult(List<PasswordError> errors) {
        super(errors);
    }
}
