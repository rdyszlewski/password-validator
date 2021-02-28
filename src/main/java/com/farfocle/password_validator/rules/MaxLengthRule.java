package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.models.PasswordError;

public class MaxLengthRule extends LengthRule {


    protected MaxLengthRule(int value) {
        super(value);
    }

    @Override
    public boolean checkLength(String password) {
        return password.length() <= getValue();
    }

    @Override
    public PasswordError getErrorType() {
        return PasswordError.TOO_LONG;
    }


    @Override
    public String getErrorMessage() {
        return String.format("Too few characters. %d characters is required", getValue());
    }

    public static class Builder extends LengthRule.Builder<Builder, MaxLengthRule> {

        public Builder(int value) {
            super(value);
        }

        @Override
        public MaxLengthRule build() {
            MaxLengthRule rule = new MaxLengthRule(value);
            super.setup(rule);
            return rule;
        }
    }
}
