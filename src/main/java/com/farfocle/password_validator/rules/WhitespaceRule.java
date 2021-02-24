package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.PasswordData;
import com.farfocle.password_validator.PasswordError;
import com.farfocle.password_validator.PasswordRuleResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WhitespaceRule extends Rule {

    private static final String PATTERN = "(\\s)";
    private final Pattern pattern;
    // TODO: to być może to będzie można przenieść gdzieś wyżej. Na pewno pozytywny
    private final PasswordRuleResult successResult;
    private final PasswordRuleResult failResult;

    private WhitespaceRule() {
        pattern = Pattern.compile(PATTERN);
        successResult = PasswordRuleResult.createSuccess();
        // TODO: przemyśleć, czy tutaj powinny być wstawione jakieś informacje
        failResult = PasswordRuleResult.createFail(getErrorType(), null);
    }

    @Override
    public PasswordRuleResult validatePassword(PasswordData password) {
        if (validatePasswordSimple(password)) {
            return successResult;
        }
        return failResult;
    }

    @Override
    public boolean validatePasswordSimple(PasswordData password) {
        Matcher matcher = pattern.matcher(password.getPassword());
        return !matcher.find();
    }

    @Override
    public PasswordError getErrorType() {
        return PasswordError.WHITESPACE;
    }

    public static class Builder extends BaseRuleBuilder<Builder, WhitespaceRule> {

        @Override
        public WhitespaceRule build() {
            WhitespaceRule rule = new WhitespaceRule();
            super.setup(rule);
            return rule;
        }
    }
}
