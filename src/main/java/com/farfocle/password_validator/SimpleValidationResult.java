package com.farfocle.password_validator;

import java.util.List;

public class SimpleValidationResult extends BaseValidationResult<PasswordError>{

    public SimpleValidationResult(List<PasswordError> errors) {
        super(errors);
    }
}
