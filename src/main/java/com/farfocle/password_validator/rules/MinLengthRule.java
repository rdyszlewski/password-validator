package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.models.PasswordError;


public class MinLengthRule extends LengthRule {

    private MinLengthRule(int value) {
        super(value);
    }

    @Override
    public boolean checkLength(String password) {
        return password.length() >= getValue();
    }

    @Override
    public PasswordError getErrorType() {
        return PasswordError.TOO_SHORT;
    }

    @Override
    public String getErrorMessage() {
        return String.format("Too much characters. Maximum %d characters are allowed", getValue());
    }

    public static class Builder extends LengthRule.Builder<Builder, MinLengthRule> {

        public Builder(int value) {
            super(value);
        }

        @Override
        public MinLengthRule build() {
            MinLengthRule rule = new MinLengthRule(value);
            super.setup(rule);
            return rule;
        }
    }
}
