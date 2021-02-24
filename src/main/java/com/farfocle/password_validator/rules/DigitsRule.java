package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.PasswordError;


public class DigitsRule extends CharactersRule {

    private DigitsRule(int value) {
        super(value);
    }

    @Override
    protected boolean checkCharacter(int character) {
        return Character.isDigit(character);
    }

    @Override
    public PasswordError getErrorType() {
        return PasswordError.DIGITS;
    }

    @Override
    public String getErrorMessage() {
        return String.format("Too few digits. %d digits is required", getValue());
    }

    public static Builder builder(int value) {
        return new Builder(value);
    }

    public static class Builder extends CharactersRule.Builder<Builder, DigitsRule> {

        public Builder(int value) {
            super(value);
        }

        @Override
        public DigitsRule build() {
            DigitsRule rule = new DigitsRule(value);
            super.setup(rule);
            return rule;
        }
    }

}
