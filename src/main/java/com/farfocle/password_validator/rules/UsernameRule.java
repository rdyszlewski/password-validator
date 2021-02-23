package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.*;

import java.util.HashMap;
import java.util.Map;

public class UsernameRule extends Rule {

    private final PasswordRuleResult successResult;
    private final PasswordRuleResult failResult;

    private UsernameRule(){
        this.successResult = PasswordRuleResult.createSuccess();
        // TODO: zastanowić się, jak to można zrobić
        this.failResult = PasswordRuleResult.createFail(getErrorType(), null);
    }

    @Override
    public PasswordRuleResult validate(PasswordData password) {
        if(!validateSimple(password)){
            return successResult;
        }
        return failResult;
    }

    @Override
    public boolean validateSimple(PasswordData password) {
        return password.getPassword().equals(password.getUsername());
    }

    @Override
    public PasswordError getErrorType() {
        return PasswordError.USERNAME;
    }

    public static class Builder extends BaseRuleBuilder<Builder, UsernameRule>{

        @Override
        public UsernameRule build() {
            UsernameRule rule = new UsernameRule();
            super.setup(rule);
            return rule;
        }
    }
}
