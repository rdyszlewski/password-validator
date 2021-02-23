package com.farfocle.password_validator;

import java.util.Collections;
import java.util.Map;

public final class PasswordRuleResult {

    private final PasswordError errorType;
    private final Map<InfoType, String> errorInfo;

    private PasswordRuleResult(){
        errorType = null;
        errorInfo = null;
    }

    private PasswordRuleResult(PasswordError errorType, Map<InfoType, String> errorInfo){
        this.errorType = errorType;
        this.errorInfo = errorInfo;
    }

    public boolean isValid(){
        return errorType == null;
    }

    public PasswordError getErrorType(){
        return this.errorType;
    }

    public Map<InfoType, String> getErrorInfo(){
        if(errorType != null){
            return Collections.unmodifiableMap(errorInfo);
        }
        return null;
    }

    public static PasswordRuleResult createSuccess(){
        return new PasswordRuleResult();
    }

    public static PasswordRuleResult createFail(PasswordError errorType, Map<InfoType, String> errorInfo){
        return new PasswordRuleResult(errorType, errorInfo);
    }
}
