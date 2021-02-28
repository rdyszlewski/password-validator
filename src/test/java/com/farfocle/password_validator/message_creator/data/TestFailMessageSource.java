package com.farfocle.password_validator.message_creator.data;

import com.farfocle.password_validator.models.InfoType;
import com.farfocle.password_validator.models.PasswordError;
import com.farfocle.password_validator.message_creator.MessageCreatorSource;
import com.farfocle.password_validator.message_creator.MessageFunction;

import java.util.Map;
import java.util.TreeMap;

public class TestFailMessageSource implements MessageCreatorSource {

    @Override
    public Map<PasswordError, MessageFunction> getMessages() {
        Map<PasswordError, MessageFunction> messages = new TreeMap<>() {{
            put(PasswordError.TOO_SHORT, result -> String.format("Min: %s", result.getErrorInfo().get(InfoType.VALID)));
            put(PasswordError.TOO_LONG, result -> String.format("Max: %s %s", result.getErrorInfo().get(InfoType.VALID), result.getErrorInfo().get(InfoType.INVALID)));
            put(PasswordError.LOWERCASE, result -> String.format("Lowercase: %s", result.getErrorInfo().get(InfoType.VALID)));
            put(PasswordError.UPPERCASE, result -> String.format("Uppercase: %s", result.getErrorInfo().get(InfoType.VALID)));
            put(PasswordError.DIGITS, result -> String.format("Digits: %s", result.getErrorInfo().get(InfoType.VALID)));
            put(PasswordError.USERNAME, result -> "Nie ma usernama");
        }};
        return messages;
    }
}
