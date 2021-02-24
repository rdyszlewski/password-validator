package com.farfocle.password_validator.message_creator;

import com.farfocle.password_validator.PasswordRuleResult;

import java.util.List;

public interface ValidationMessageCreator {
    String getMessage(PasswordRuleResult result);

    boolean validate(List<MessageValidationRule> rules) throws MessageCreatorValidationException;
}
