package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.ErrorDetails;
import com.farfocle.password_validator.PasswordData;
import com.farfocle.password_validator.PasswordError;

//public class UsernameRule implements Rule {
//
//    private ErrorDetails errorDetails;
//    private boolean interrupting;
//
//    public UsernameRule() {
//        init();
//    }
//
//    public UsernameRule(boolean interrupting) {
//        init();
//        this.interrupting = interrupting;
//    }
//
//    private void init() {
//        errorDetails = new ErrorDetails(PasswordError.USERNAME, null);
//    }
//
//    @Override
//    public boolean validate(PasswordData password) throws NullPointerException {
//        if (password == null || password.getPassword() == null || password.getUsername() == null) {
//            throw new NullPointerException();
//        }
//        return !password.getPassword().equals(password.getUsername());
//    }
//
//    @Override
//    public ErrorDetails getErrorDetails() {
//        return errorDetails;
//    }
//
//    @Override
//    public boolean isInterrupting() {
//        return interrupting;
//    }
//}
