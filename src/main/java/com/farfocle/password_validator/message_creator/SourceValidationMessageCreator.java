package com.farfocle.password_validator.message_creator;

import com.farfocle.password_validator.InfoType;
import com.farfocle.password_validator.PasswordError;
import com.farfocle.password_validator.PasswordRuleResult;

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
    public boolean validate(List<MessageValidationRule> rules) throws MessageCreatorValidationException {
        for (MessageValidationRule rule : rules) {
            Map<InfoType, String> map = new HashMap<>();
            for (InfoType infoType : rule.getAvailableInfo()) {
                map.put(infoType, infoType.name());
            }
            PasswordRuleResult testResult = PasswordRuleResult.createFail(rule.getError(), map);
            String message = getMessage(testResult);
            if (message.contains("null")) {
                for (InfoType infoType : rule.getAvailableInfo()) {
                    if (!message.contains(infoType.name())) {
                        throw new MessageCreatorValidationException(rule.getError(), infoType);
                    }
                }
                // TODO: nie wiemy jakie dokładnie się wykrzaczyło. Będzie to trzeba sprawdzić. Zmienic to Valid
            }
        }
        return true;
    }
}
