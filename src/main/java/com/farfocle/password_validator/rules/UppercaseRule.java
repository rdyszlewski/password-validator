package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.models.PasswordError;

public class UppercaseRule extends CharactersRule {


    private UppercaseRule(int value) {
        super(value);
    }

    @Override
    protected boolean checkCharacter(int character) {
        return Character.isUpperCase(character);
    }

    @Override
    public PasswordError getErrorType() {
        return PasswordError.UPPERCASE;
    }

    @Override
    public String getErrorMessage() {
        return String.format("Too few uppercase characters. %d uppercase characters is required", getValue());
    }

    public static class Builder extends CharactersRule.Builder<Builder, UppercaseRule> {

        public Builder(int value) {
            super(value);
        }

        @Override
        public UppercaseRule build() {
            UppercaseRule rule = new UppercaseRule(value);
            super.setup(rule);
            return rule;
        }
    }
}
