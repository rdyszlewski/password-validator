package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.models.PasswordError;

public class LowercaseRule extends CharactersRule {

    private LowercaseRule(int value) {
        super(value);
    }

    @Override
    protected boolean checkCharacter(int character) {
        return Character.isLowerCase(character);
    }

    @Override
    public PasswordError getErrorType() {
        return PasswordError.LOWERCASE;
    }

    @Override
    public String getErrorMessage() {
        return String.format("Too few lowercase characters. %d lowercase characters is required", getValue());

    }

    public static class Builder extends CharactersRule.Builder<Builder, LowercaseRule> {

        public Builder(int value) {
            super(value);
        }

        @Override
        public LowercaseRule build() {
            LowercaseRule rule = new LowercaseRule(value);
            super.setup(rule);
            return rule;
        }
    }
}
