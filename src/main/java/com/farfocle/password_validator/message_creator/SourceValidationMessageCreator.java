package com.farfocle.password_validator.message_creator;

import com.farfocle.password_validator.PasswordError;
import com.farfocle.password_validator.PasswordRuleResult;

import java.util.List;
import java.util.Map;

public class SourceValidationMessageCreator implements ValidationMessageCreator{

    private final Map<PasswordError, MessageFunction> messages;

    public SourceValidationMessageCreator(MessageCreatorSource source){
        this.messages = source.getMessages();
    }

    @Override
    public String getMessage(PasswordRuleResult result) {
        if(result != null){
            assert messages.containsKey(result.getErrorType());
            return messages.get(result.getErrorType()).getMessage(result);
        }
        return null;
    }

    @Override
    public boolean validate(List<PasswordError> errors) {
         return errors.stream().allMatch(messages::containsKey);
    }
}
