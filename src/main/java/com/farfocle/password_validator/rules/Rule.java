package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.InfoType;
import com.farfocle.password_validator.PasswordData;
import com.farfocle.password_validator.PasswordError;
import com.farfocle.password_validator.PasswordRuleResult;
import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;

import java.util.List;

public abstract class Rule {

    private boolean interrupting;

    public PasswordRuleResult validate(PasswordData passwordData) throws InvalidPasswordDataException {
        checkData(passwordData);
        return validatePassword(passwordData);
    }

    protected abstract PasswordRuleResult validatePassword(PasswordData password) throws InvalidPasswordDataException;
    protected abstract boolean validatePasswordSimple(PasswordData password);
    public abstract PasswordError getErrorType();
    public abstract List<InfoType> getAvailableInfoType();
    public abstract String getErrorMessage();


    private void checkData(PasswordData passwordData) throws InvalidPasswordDataException {
        if (passwordData == null) {
            throw new InvalidPasswordDataException(InvalidPasswordDataException.Type.DATA_NULL);
        }
        if (passwordData.getPassword() == null) {
            throw new InvalidPasswordDataException(InvalidPasswordDataException.Type.PASSWORD_NULL);
        }
    }

    public boolean validateSimple(PasswordData passwordData) throws InvalidPasswordDataException {
        checkData(passwordData);
        return validatePasswordSimple(passwordData);
    }

    public boolean isInterrupting() {
        return interrupting;
    }

    static abstract class BaseRuleBuilder<T extends BaseRuleBuilder<T, K>, K extends Rule> {

        private boolean interrupting;

        public T setInterrupting() {
            return setInterrupting(true);
        }

        public T setInterrupting(boolean interrupting) {
            this.interrupting = interrupting;
            return (T) this;
        }

        protected void setup(Rule rule) {
            rule.interrupting = interrupting;
        }

        public abstract K build();
    }
}
