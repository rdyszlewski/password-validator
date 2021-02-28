package com.farfocle.password_validator.models;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public final class PasswordRuleResult {

    private final PasswordError errorType;
    private final Map<InfoType, String> errorInfo;

    private PasswordRuleResult() {
        errorType = null;
        errorInfo = null;
    }

    private PasswordRuleResult(PasswordError errorType, Map<InfoType, String> errorInfo) {
        this.errorType = errorType;
        this.errorInfo = errorInfo;
    }

    public boolean isValid() {
        return errorType == null;
    }

    public PasswordError getErrorType() {
        return this.errorType;
    }

    public Map<InfoType, String> getErrorInfo() {
        if (errorType != null) {
            return Collections.unmodifiableMap(errorInfo);
        }
        return null;
    }

    public static PasswordRuleResult createSuccess() {
        return new PasswordRuleResult();
    }

    public static PasswordRuleResult createFail(PasswordError errorType, Map<InfoType, String> errorInfo) {
        return new PasswordRuleResult(errorType, errorInfo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordRuleResult that = (PasswordRuleResult) o;
        return errorType == that.errorType &&
                Objects.equals(errorInfo, that.errorInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorType, errorInfo);
    }
}
