package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.models.InfoType;
import com.farfocle.password_validator.models.PasswordData;
import com.farfocle.password_validator.models.PasswordError;
import com.farfocle.password_validator.models.PasswordRuleResult;
import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;

import java.util.LinkedList;
import java.util.List;

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
        if (password.getUsername() == null) {
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

    @Override
    public List<InfoType> getAvailableInfoType() {
        assert failResult.getErrorInfo() != null;
        return new LinkedList<>(failResult.getErrorInfo().keySet());
    }

    @Override
    public String getErrorMessage() {
        return "The password cannot be the same as the username";
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
