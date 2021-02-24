package com.farfocle.password_validator.message_creator;

import com.farfocle.password_validator.InfoType;
import com.farfocle.password_validator.PasswordError;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestMessageSource implements MessageCreatorSource{

    @Override
    public Map<PasswordError, MessageFunction> getMessages() {
        Map<PasswordError, MessageFunction> messages = new ConcurrentHashMap<>(){{
           put(PasswordError.TOO_SHORT, result -> "Za krótkie");
           put(PasswordError.TOO_LONG, result -> "Za długie " + result.getErrorInfo().get(InfoType.VALID));
            put(PasswordError.USERNAME, result -> "Nie ma usernama");
        }};
        return messages;
    }
}
