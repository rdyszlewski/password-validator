package com.farfocle.password_validator;



import java.util.Collections;

import java.util.Map;

public final class ErrorDetails {
    private final PasswordError errorType;
    private final Map<InfoType, String> errorInfo;
    private final String message;

    public ErrorDetails(PasswordError errorType, Map<InfoType, String> errorInfo, String message) {
        this.errorType = errorType;
        this.errorInfo = errorInfo;
        this.message = message;
    }

    public PasswordError getErrorType() {
        return errorType;
    }

    public Map<InfoType, String> getErrorInfo() {
        return Collections.unmodifiableMap(this.errorInfo);
    }

    public String getMessage() {
        return message;
    }
}
