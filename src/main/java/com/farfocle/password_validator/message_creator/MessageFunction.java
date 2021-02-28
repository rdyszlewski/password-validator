package com.farfocle.password_validator.message_creator;

import com.farfocle.password_validator.models.PasswordRuleResult;

@FunctionalInterface
public interface MessageFunction {
    String getMessage(PasswordRuleResult result);
}
