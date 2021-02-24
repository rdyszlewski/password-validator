package com.farfocle.password_validator;

import com.farfocle.password_validator.cache.ErrorDetailsCache;
import com.farfocle.password_validator.message_creator.ValidationMessageCreator;
import com.farfocle.password_validator.rules.Rule;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordValidator implements IPasswordValidator {

    private final List<Rule> rules;
    private final List<PasswordError> availableErrors;
    private ErrorDetailsCache errorDetailsCache;
    private ValidationMessageCreator errorMessageCreator;

    public PasswordValidator(final List<Rule> rules){
        this.rules = rules;
        availableErrors = rules.stream().map(Rule::getErrorType).collect(Collectors.toUnmodifiableList());
    }

    public void setCache(int size){
        errorDetailsCache = new ErrorDetailsCache(size);
    }

    public void setMessageCreator(ValidationMessageCreator creator){
        assert creator != null;
        // TODO: przygotować dane do walidacji
        // TODO: uruchomić walidację
        this.errorMessageCreator = creator;
    }

    @Override
    public ValidationResult validate(PasswordData passwordData) {
        List<ErrorDetails> errors = new LinkedList<>();
        for(Rule rule: rules){
            PasswordRuleResult ruleResult = rule.validate(passwordData);
            if(!ruleResult.isValid()){
                errors.add(getErrorDetails(ruleResult, rule));
                if(rule.isInterrupting()){
                    return new ValidationResult(errors);
                }
            }
        }
        return new ValidationResult(errors);
    }

    private ErrorDetails getErrorDetails(PasswordRuleResult ruleResult, Rule rule){
        if(errorDetailsCache != null){
            return errorDetailsCache.getErrorDetails(ruleResult, x-> createErrorDetails(ruleResult, rule));
        }
        return createErrorDetails(ruleResult, rule);
    }

    private ErrorDetails createErrorDetails(PasswordRuleResult ruleResult, Rule rule){
        return new ErrorDetails(ruleResult.getErrorType(), ruleResult.getErrorInfo(), null);
    }

    private String getErrorMessage(PasswordRuleResult ruleResult, Rule rule){
        if(errorMessageCreator != null){
            return errorMessageCreator.getMessage(ruleResult);
        }
        // TODO: zwrócić standardową wiadomość z regułyu
        return "";
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
