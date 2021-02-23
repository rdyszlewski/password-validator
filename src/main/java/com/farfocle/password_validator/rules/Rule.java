package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.PasswordData;
import com.farfocle.password_validator.PasswordError;
import com.farfocle.password_validator.PasswordRuleResult;

public abstract class Rule {

    private boolean interrupting;

    public abstract PasswordRuleResult validate(PasswordData password);
    public abstract boolean validateSimple(PasswordData password);
    public abstract PasswordError getErrorType();

    public boolean isInterrupting(){
        return interrupting;
    }

    static abstract class BaseRuleBuilder <T extends BaseRuleBuilder<T,K>, K extends Rule>{

        private boolean interrupting;

        public T setInterrupting(){
            return setInterrupting(true);
        }
        public T setInterrupting(boolean interrupting){
            this.interrupting = interrupting;
            return (T)this;
        }

        protected void setup(Rule rule){
            rule.interrupting = interrupting;
        }

        public abstract K build();
    }
}
