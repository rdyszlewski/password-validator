package com.farfocle.password_validator;

import com.farfocle.password_validator.cache.ErrorDetailsCache;
import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;
import com.farfocle.password_validator.message_creator.MessageCreatorValidationException;
import com.farfocle.password_validator.message_creator.MessageValidationRule;
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
        assert size > 0;
        this.errorDetailsCache = new ErrorDetailsCache(size);
    }

    public void setErrorMessageCreator(ValidationMessageCreator creator) throws MessageCreatorValidationException {
        assert creator != null;
        // TODO: zrobić walidację
        List<MessageValidationRule> messageValidationRules = prepareMessageValidationRules();
        if(creator.validate(messageValidationRules)){
            this.errorMessageCreator = creator;
        }
    }

    private List<MessageValidationRule> prepareMessageValidationRules() {
        List<MessageValidationRule> messageValidationRules = new LinkedList<>();
        for(Rule rule: rules){
            MessageValidationRule validationRule = new MessageValidationRule(rule.getErrorType(), rule.getAvailableInfoType());
            messageValidationRules.add(validationRule);
        }
        return messageValidationRules;
    }

    @Override
    public ValidationResult validate(PasswordData passwordData) throws InvalidPasswordDataException {
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
            return errorDetailsCache.getErrorDetails(ruleResult, x->createErrorDetails(ruleResult, rule));
        }
        return createErrorDetails(ruleResult, rule);
    }

    private ErrorDetails createErrorDetails(PasswordRuleResult ruleResult, Rule rule){
        String errorMessage = getErrorMessage(ruleResult, rule);
        return new ErrorDetails(ruleResult.getErrorType(), ruleResult.getErrorInfo(), errorMessage);
    }

    private String getErrorMessage(PasswordRuleResult result, Rule rule){
        if(errorMessageCreator != null){
            return errorMessageCreator.getMessage(result);
        }
        return rule.getErrorMessage();
    }

    @Override
    public SimpleValidationResult validateErrors(PasswordData passwordData) throws InvalidPasswordDataException {
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
    public boolean validateSimple(PasswordData passwordData) throws InvalidPasswordDataException {
        for(Rule rule: rules){
            if(!rule.validateSimple(passwordData)){
                return false;
            }
        }
        return true;
    }

    @Override
    public List<PasswordError> getAvailableErrors() {
        return availableErrors;
    }
}
