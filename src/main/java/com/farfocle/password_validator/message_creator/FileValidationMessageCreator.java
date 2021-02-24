package com.farfocle.password_validator.message_creator;

import com.farfocle.password_validator.PasswordError;
import com.farfocle.password_validator.PasswordRuleResult;
import com.farfocle.password_validator.ValidationResult;

import javax.lang.model.type.ErrorType;
import java.util.List;

public class FileValidationMessageCreator implements ValidationMessageCreator {

    @Override
    public String getMessage(PasswordRuleResult result) {
        return null;
    }

    @Override
    public boolean validate(List<PasswordError> errors) {
        return false;
    }
}
