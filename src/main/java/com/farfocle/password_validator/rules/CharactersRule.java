package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.InfoType;
import com.farfocle.password_validator.PasswordData;
import com.farfocle.password_validator.PasswordRuleResult;

import java.util.HashMap;
import java.util.Map;

public abstract class CharactersRule extends Rule {

    private final int value;

    private final PasswordRuleResult successResult;
    private final PasswordRuleResult failResult;

    public CharactersRule(int value){
        this.value = value;
        this.successResult = PasswordRuleResult.createSuccess();
        Map<InfoType, String> errorInfo = new HashMap<InfoType, String>(){{
           put(InfoType.VALID, String.valueOf(value));
        }};
        this.failResult = PasswordRuleResult.createFail(getErrorType(), errorInfo);
    }

    public int getValue(){
        return this.value;
    }

    @Override
    public PasswordRuleResult validate(PasswordData passwordData){
        long validCharactersCount = countValidCharacters(passwordData.getPassword());
        // TODO: zrobić tutaj też przygotowanie specjalnej odpowiedzi
        if(checkValid(validCharactersCount)){
            return successResult;
        }
        return failResult;
    }

    @Override
    public boolean validateSimple(PasswordData passwordData){
        long validCharactersCount = countValidCharacters(passwordData.getPassword());
        return checkValid(validCharactersCount);
    }

    private boolean checkValid(long validCharactersCount){
        return validCharactersCount >= this.value;
    }

    private long countValidCharacters(String password){
        return password.chars().filter(this::checkCharacter).limit(value).count();
    }

    protected abstract boolean checkCharacter(int character);

    static abstract class Builder<T extends Builder<T, K>, K extends CharactersRule> extends BaseRuleBuilder<T, K>{

        protected int value;

        public Builder(int value){
            super();
            this.value = value;
        }

        protected void setup(Rule rule){
            super.setup(rule);
        }

        public abstract K build();
    }
}
