package com.farfocle.password_validator;


import com.farfocle.password_validator.rules.DigitsRule;

import java.util.HashMap;
import java.util.Map;

public final class ErrorDetails {
    private final PasswordError errorType;
    private final Map<InfoType, String> parameters;

    public ErrorDetails(PasswordError errorType) {
        this.errorType = errorType;
        this.parameters = new HashMap<>();
    }

    public PasswordError getErrorType() {
        return errorType;
    }

    void addParameter(InfoType infoType, String value){
        this.parameters.put(infoType, value);
    }


}
