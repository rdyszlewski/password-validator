package com.farfocle.password_validator.message_creator;

import com.farfocle.password_validator.PasswordError;
import com.farfocle.password_validator.PasswordRuleResult;

import javax.lang.model.type.ErrorType;
import java.util.List;

public interface ValidationMessageCreator {
    String getMessage(PasswordRuleResult result);
    boolean validate(List<PasswordError> errors);
}
