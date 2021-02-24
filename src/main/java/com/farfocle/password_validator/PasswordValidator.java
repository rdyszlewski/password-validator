package com.farfocle.password_validator;

import com.farfocle.password_validator.rules.Rule;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordValidator implements IPasswordValidator {

    private final List<Rule> rules;
    private final List<PasswordError> availableErrors;

    public PasswordValidator(final List<Rule> rules){
        this.rules = rules;
        availableErrors = rules.stream().map(Rule::getErrorType).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public ValidationResult validate(PasswordData passwordData) {
        List<ErrorDetails> errors = new LinkedList<>();
        for(Rule rule: rules){
            PasswordRuleResult ruleResult = rule.validate(passwordData);
            if(!ruleResult.isValid()){
                errors.add(getErrorDetails(ruleResult));
                if(rule.isInterrupting()){
                    return new ValidationResult(errors);
                }
            }
        }
        return new ValidationResult(errors);
    }

    public ErrorDetails getErrorDetails(PasswordRuleResult ruleResult){
        // TODO: doadć tutaj jakiś cache do tego
        return new ErrorDetails(ruleResult.getErrorType(), ruleResult.getErrorInfo(), null);
    }

    @Override
    public SimpleValidationResult validateErrors(PasswordData passwordData) {
        List<PasswordError> errors = new LinkedList<>();
        for(Rule rule: rules){
            if(!rule.validateSimple(passwordData)){
                errors.add(rule.getErrorType());
                if(rule.isInterrupting()){
                    return new SimpleValidationResult(errors);
                }
            }
        }
        return new SimpleValidationResult(errors);
    }

    @Override
    public boolean validateSimple(PasswordData passwordData) {
        return rules.stream().allMatch(rule->rule.validateSimple(passwordData));
//        for(Rule rule: rules){
//            if(!rule.validateSimple(passwordData)){
//                return false;
//            }
//        }
//        return true;
    }

    @Override
    public List<PasswordError> getAvailableErrors() {
        return availableErrors;
    }
}
