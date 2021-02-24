package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.PasswordData;
import com.farfocle.password_validator.PasswordError;
import com.farfocle.password_validator.PasswordRuleResult;
import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;

public class UsernameRule extends Rule {

    private final PasswordRuleResult successResult;
    private final PasswordRuleResult failResult;

    private UsernameRule() {
        this.successResult = PasswordRuleResult.createSuccess();
        // TODO: zastanowić się, jak to można zrobić
        this.failResult = PasswordRuleResult.createFail(getErrorType(), null);
    }

    @Override
    public PasswordRuleResult validatePassword(PasswordData password) throws InvalidPasswordDataException {
        if(password.getUsername() == null){
            throw new InvalidPasswordDataException(InvalidPasswordDataException.Type.USERNAME_NULL);
        }
        if (!validatePasswordSimple(password)) {
            return successResult;
        }
        return failResult;
    }

    @Override
    public boolean validatePasswordSimple(PasswordData password) {
        return password.getPassword().equals(password.getUsername());
    }

    @Override
    public PasswordError getErrorType() {
        return PasswordError.USERNAME;
    }

    public static class Builder extends BaseRuleBuilder<Builder, UsernameRule> {

        @Override
        public UsernameRule build() {
            UsernameRule rule = new UsernameRule();
            super.setup(rule);
            return rule;
        }
    }
}
