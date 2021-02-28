package com.farfocle.password_validator;

import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;
import com.farfocle.password_validator.models.*;
import com.farfocle.password_validator.rules.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class PasswordValidatorTest {

    private  MinLengthRule minLengthRule;
    private  MaxLengthRule maxLengthRule;
    private  UppercaseRule uppercaseRule;
    private  DigitsRule digitsRule;

    private IPasswordValidator passwordValidator;
    private final PasswordData passwordData = new PasswordData("dkasj34D");

    @Before
    public void initValidator(){
        minLengthRule = Mockito.mock(MinLengthRule.class);
        maxLengthRule = Mockito.mock(MaxLengthRule.class);
        uppercaseRule = Mockito.mock(UppercaseRule.class);
        digitsRule = Mockito.mock(DigitsRule.class);


        when(minLengthRule.getErrorType()).thenReturn(PasswordError.TOO_SHORT);
        when(maxLengthRule.getErrorType()).thenReturn(PasswordError.TOO_LONG);
        when(uppercaseRule.getErrorType()).thenReturn(PasswordError.UPPERCASE);
        when(digitsRule.getErrorType()).thenReturn(PasswordError.DIGITS);
        List<Rule> rules = Arrays.asList(minLengthRule, maxLengthRule, uppercaseRule, digitsRule);
        passwordValidator = new PasswordValidator(rules);
    }

    @Test
    public void shouldReturnValid() throws InvalidPasswordDataException {
        prepareRule(minLengthRule, PasswordRuleResult.createSuccess(), false, passwordData);
        prepareRule(maxLengthRule, PasswordRuleResult.createSuccess(), false, passwordData);
        prepareRule(uppercaseRule, PasswordRuleResult.createSuccess(), false, passwordData);
        prepareRule(digitsRule, PasswordRuleResult.createSuccess(), false, passwordData);

        ValidationResult validationResult = passwordValidator.validate(passwordData);
        assertTrue(validationResult.isValid());
        assertEquals(0, validationResult.getErrors().size());


    }

    @Test
    public void shouldNotInterruptWhenValid() throws InvalidPasswordDataException {
        prepareRule(minLengthRule, PasswordRuleResult.createSuccess(), true, passwordData);
        prepareRule(maxLengthRule, PasswordRuleResult.createSuccess(), true, passwordData);
        prepareRule(uppercaseRule, PasswordRuleResult.createSuccess(), true, passwordData);
        prepareRule(digitsRule, PasswordRuleResult.createSuccess(), true, passwordData);

        ValidationResult validationResult = passwordValidator.validate(passwordData);
        assertTrue(validationResult.isValid());
        assertEquals(0, validationResult.getErrors().size());
    }

    private void prepareRule(Rule rule,PasswordRuleResult result, boolean interrupting,  PasswordData password) throws InvalidPasswordDataException {
        when(rule.validate(password)).thenReturn(result);
        when(rule.isInterrupting()).thenReturn(interrupting);
    }

    @Test
    public void shouldReturnInvalidWhenHasErrors() throws InvalidPasswordDataException {
        prepareRule(minLengthRule, getMinLengthErrorResult(), false, passwordData);
        prepareRule(maxLengthRule, getMaxLengthErrorResult(), false, passwordData);
        prepareRule(uppercaseRule, getUppercaseErrorResult(), false, passwordData);
        prepareRule(digitsRule, getDigitsErrorResult(), false, passwordData);

        ValidationResult validationResult = passwordValidator.validate(passwordData);
        assertFalse(validationResult.isValid());
        assertEquals(4, validationResult.getErrors().size());
        assertEquals(PasswordError.TOO_SHORT, validationResult.getErrors().get(0).getErrorType());
        assertEquals(PasswordError.TOO_LONG, validationResult.getErrors().get(1).getErrorType());
        assertEquals(PasswordError.UPPERCASE, validationResult.getErrors().get(2).getErrorType());
        assertEquals(PasswordError.DIGITS, validationResult.getErrors().get(3).getErrorType());
    }

    private PasswordRuleResult getMinLengthErrorResult(){
        Map<InfoType, String> errorInfo = new HashMap<>();
        errorInfo.put(InfoType.VALID, "3");
        return PasswordRuleResult.createFail(PasswordError.TOO_SHORT, errorInfo);
    }

    private PasswordRuleResult getMaxLengthErrorResult(){
        Map<InfoType, String> errorInfo = new HashMap<>();
        errorInfo.put(InfoType.VALID, "20");
        return PasswordRuleResult.createFail(PasswordError.TOO_LONG, errorInfo);
    }

    private PasswordRuleResult getUppercaseErrorResult(){
        Map<InfoType, String> errorInfo = new HashMap<>();
        errorInfo.put(InfoType.VALID, "1");
        return PasswordRuleResult.createFail(PasswordError.UPPERCASE, errorInfo);
    }

    public PasswordRuleResult getDigitsErrorResult(){
        Map<InfoType, String> errorInfo = new HashMap<>();
        errorInfo.put(InfoType.VALID, "1");
        return PasswordRuleResult.createFail(PasswordError.DIGITS, errorInfo);
    }

    @Test
    public void shouldInterruptAfterFailInterruptingRule() throws InvalidPasswordDataException {
        prepareRule(minLengthRule, PasswordRuleResult.createSuccess(), true, passwordData);
        prepareRule(maxLengthRule, getMaxLengthErrorResult(), true, passwordData);
        prepareRule(uppercaseRule, getUppercaseErrorResult(), true, passwordData);
        prepareRule(digitsRule, getDigitsErrorResult(), true, passwordData);

        ValidationResult result = passwordValidator.validate(passwordData);
        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        assertEquals(PasswordError.TOO_LONG, result.getErrors().get(0).getErrorType());

        prepareRule(minLengthRule, PasswordRuleResult.createSuccess(), true, passwordData);
        prepareRule(maxLengthRule, getMaxLengthErrorResult(), false, passwordData);
        prepareRule(uppercaseRule, getUppercaseErrorResult(), true, passwordData);
        prepareRule(digitsRule, getDigitsErrorResult(), true, passwordData);

        ValidationResult result2 = passwordValidator.validate(passwordData);
        assertFalse(result2.isValid());
        assertEquals(2, result2.getErrors().size());
        assertEquals(PasswordError.TOO_LONG, result2.getErrors().get(0).getErrorType());
        assertEquals(PasswordError.UPPERCASE, result2.getErrors().get(1).getErrorType());
    }
}
