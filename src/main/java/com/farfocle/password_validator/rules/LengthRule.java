package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.InfoType;
import com.farfocle.password_validator.PasswordData;
import com.farfocle.password_validator.PasswordError;
import com.farfocle.password_validator.PasswordRuleResult;

import java.util.HashMap;
import java.util.Map;

public abstract class LengthRule extends Rule {

    private final int value;
    private final PasswordRuleResult successResult;
    private final PasswordRuleResult failResult;

    protected LengthRule(int value) {
        this.value = value;
        successResult = PasswordRuleResult.createSuccess();
        Map<InfoType, String> errorInfo = new HashMap<InfoType, String>() {{
            put(InfoType.VALID, String.valueOf(value));
        }};
        failResult = PasswordRuleResult.createFail(getErrorType(), errorInfo);
    }

    @Override
    public PasswordRuleResult validatePassword(PasswordData password) {
        if (checkLength(password.getPassword())) {
            return successResult;
        }
        // TODO: zrobić przygotowanie błędy jeśli trzeba
        return failResult;
    }

    @Override
    public boolean validatePasswordSimple(PasswordData password) {
        return checkLength(password.getPassword());
    }

    public int getValue() {
        return value;
    }

    public abstract boolean checkLength(String password);

    public abstract PasswordError getErrorType();

    protected static abstract class Builder<T extends Builder<T, K>, K extends LengthRule> extends BaseRuleBuilder<T, K> {

        protected int value;

        public Builder(int value) {
            super();
            this.value = value;
        }

        protected void setup(Rule rule) {
            super.setup(rule);
        }

        public abstract K build();
    }

}
