package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.InfoType;
import com.farfocle.password_validator.PasswordData;
import com.farfocle.password_validator.PasswordRuleResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class CharactersRule extends Rule {

    private final int value;

    private final PasswordRuleResult successResult;
    private final PasswordRuleResult failResult;

    public CharactersRule(int value) {
        this.value = value;
        this.successResult = PasswordRuleResult.createSuccess();
        Map<InfoType, String> errorInfo = new HashMap<InfoType, String>() {{
            put(InfoType.VALID, String.valueOf(value));
        }};
        this.failResult = PasswordRuleResult.createFail(getErrorType(), errorInfo);
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public PasswordRuleResult validatePassword(PasswordData passwordData) {
        long validCharactersCount = countValidCharacters(passwordData.getPassword());
        // TODO: zrobić tutaj też przygotowanie specjalnej odpowiedzi
        if (checkValid(validCharactersCount)) {
            return successResult;
        }
        return failResult;
    }

    @Override
    public boolean validatePasswordSimple(PasswordData passwordData) {
        long validCharactersCount = countValidCharacters(passwordData.getPassword());
        return checkValid(validCharactersCount);
    }

    private boolean checkValid(long validCharactersCount) {
        return validCharactersCount >= this.value;
    }

    private long countValidCharacters(String password) {
        return password.chars().filter(this::checkCharacter).limit(value).count();
    }

    protected abstract boolean checkCharacter(int character);

    @Override
    public List<InfoType> getAvailableInfoType() {
        assert failResult.getErrorInfo() != null;
        return new ArrayList<>(failResult.getErrorInfo().keySet());
    }

    static abstract class Builder<T extends Builder<T, K>, K extends CharactersRule> extends BaseRuleBuilder<T, K> {

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
