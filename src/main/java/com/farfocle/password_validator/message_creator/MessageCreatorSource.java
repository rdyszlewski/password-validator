package com.farfocle.password_validator.message_creator;

import com.farfocle.password_validator.models.PasswordError;

import java.util.Map;

public interface MessageCreatorSource {
    Map<PasswordError, MessageFunction> getMessages();
}
