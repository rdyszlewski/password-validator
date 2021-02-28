package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.models.InfoType;
import com.farfocle.password_validator.models.PasswordData;
import com.farfocle.password_validator.models.PasswordError;
import com.farfocle.password_validator.models.PasswordRuleResult;

import java.util.LinkedList;
import java.util.List;
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

    @Override
    public List<InfoType> getAvailableInfoType() {

        assert failResult.getErrorInfo() != null;
        return new LinkedList<>(failResult.getErrorInfo().keySet());
    }

    @Override
    public String getErrorMessage() {
        return "The password cannot contain spaces";
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
