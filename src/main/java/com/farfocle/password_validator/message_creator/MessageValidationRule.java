package com.farfocle.password_validator.message_creator;

import com.farfocle.password_validator.InfoType;
import com.farfocle.password_validator.PasswordError;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MessageValidationRule {

    private final PasswordError error;
    private final Set<InfoType> availableInfo;

    public MessageValidationRule(PasswordError error, Set<InfoType> availableInfo) {
        this.error = error;
        this.availableInfo = availableInfo;
    }

    public MessageValidationRule(PasswordError error, List<InfoType> availableInfo) {
        this(error, new HashSet<>(availableInfo));
    }

    public PasswordError getError() {
        return error;
    }

    public Set<InfoType> getAvailableInfo() {
        return Collections.unmodifiableSet(availableInfo);
    }
}
