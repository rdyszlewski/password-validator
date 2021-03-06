package com.farfocle.password_validator.message_creator;

import com.farfocle.password_validator.models.InfoType;
import com.farfocle.password_validator.models.PasswordError;

public final class MessageCreatorValidationException extends RuntimeException {

    private final PasswordError error;
    private final InfoType infoType;

    public MessageCreatorValidationException(PasswordError error, InfoType infoType) {
        this.error = error;
        this.infoType = infoType;
    }

    public PasswordError getError() {
        return error;
    }

    public InfoType getInfoType() {
        return infoType;
    }

    @Override
    public String getMessage() {
        return "Not implemented " + infoType.name() + " for " + error.name();
    }
}
