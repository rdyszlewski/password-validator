package com.farfocle.password_validator.message_creator;

import com.farfocle.password_validator.models.InfoType;
import com.farfocle.password_validator.models.PasswordError;
import com.farfocle.password_validator.models.PasswordRuleResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SourceValidationMessageCreator implements ValidationMessageCreator {

    private final Map<PasswordError, MessageFunction> messages;

    public SourceValidationMessageCreator(MessageCreatorSource source) {
        this.messages = source.getMessages();
    }

    @Override
    public String getMessage(PasswordRuleResult result) {
        if (result != null) {
            assert messages.containsKey(result.getErrorType());
            return messages.get(result.getErrorType()).getMessage(result);
        }
        return null;
    }

    @Override
    public boolean validate(List<MessageValidationRule> rules) {
        for (MessageValidationRule rule : rules) {
            Map<InfoType, String> map = new HashMap<>();
            for (InfoType infoType : rule.getAvailableInfo()) {
                map.put(infoType, infoType.name());
            }
            PasswordRuleResult testResult = PasswordRuleResult.createFail(rule.getError(), map);
            String message = null;
            try {
                message = getMessage(testResult);
                if (message.contains("null")) throw new NullPointerException();
            } catch (NullPointerException e) {
                InfoType missingInfoType = getMissingInfoType(message);
                throw new MessageCreatorValidationException(rule.getError(), missingInfoType);
            }
            if (message.contains("null")) {
                InfoType missingInfoType = getMissingInfoType(message);
                throw new MessageCreatorValidationException(rule.getError(), missingInfoType);
            }
        }
        return true;
    }

    private InfoType getMissingInfoType(String message) {
        for (InfoType infoType : InfoType.values()) {
            assert message != null;
            if (!message.contains(infoType.name())) {
                return infoType;
            }
        }
        throw new IllegalArgumentException();
    }
}
