package com.farfocle.password_validator;

import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;
import com.farfocle.password_validator.models.PasswordData;
import com.farfocle.password_validator.models.PasswordError;
import com.farfocle.password_validator.models.SimpleValidationResult;
import com.farfocle.password_validator.rules.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class PasswordValidationErrorTest {

    private MinLengthRule minLengthRule;
    private MaxLengthRule maxLengthRule;
    private UppercaseRule uppercaseRule;
    private DigitsRule digitsRule;

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
    public void shouldReturnTrue() throws InvalidPasswordDataException {
        prepareRule(minLengthRule, true, false, passwordData);
        prepareRule(maxLengthRule, true, false, passwordData);
        prepareRule(uppercaseRule, true, false, passwordData);
        prepareRule(digitsRule, true, false, passwordData);

        SimpleValidationResult result = passwordValidator.validateErrors(passwordData);
        assertTrue(result.isValid());
        assertEquals(0, result.getErrors().size());
    }

    @Test
    public void shouldNotInterruptWhenValid() throws InvalidPasswordDataException {
        prepareRule(minLengthRule, true, true, passwordData);
        prepareRule(maxLengthRule, true, true, passwordData);
        prepareRule(uppercaseRule, true, true, passwordData);
        prepareRule(digitsRule, true, true, passwordData);

        SimpleValidationResult result = passwordValidator.validateErrors(passwordData);
        assertTrue(result.isValid());
        assertEquals(0, result.getErrors().size());
    }

    @Test
    public void shouldReturnInvalidWHenHasErrors() throws InvalidPasswordDataException {
        prepareRule(minLengthRule, false, false, passwordData);
        prepareRule(maxLengthRule, false, false, passwordData);
        prepareRule(uppercaseRule, false, false, passwordData);
        prepareRule(digitsRule, false, false, passwordData);

        SimpleValidationResult result = passwordValidator.validateErrors(passwordData);
        assertFalse(result.isValid());
        assertEquals(4, result.getErrors().size());
        assertEquals(PasswordError.TOO_SHORT, result.getErrors().get(0));
        assertEquals(PasswordError.TOO_LONG, result.getErrors().get(1));
        assertEquals(PasswordError.UPPERCASE, result.getErrors().get(2));
        assertEquals(PasswordError.DIGITS, result.getErrors().get(3));

        prepareRule(minLengthRule, true, false, passwordData);
        prepareRule(maxLengthRule, false, false, passwordData);
        prepareRule(uppercaseRule, true, false, passwordData);
        prepareRule(digitsRule, false, false, passwordData);

        SimpleValidationResult result2 = passwordValidator.validateErrors(passwordData);
        assertFalse(result2.isValid());
        assertEquals(2, result2.getErrors().size());
        assertEquals(PasswordError.TOO_LONG, result2.getErrors().get(0));
        assertEquals(PasswordError.DIGITS, result2.getErrors().get(1));
    }

    @Test
    public void shouldInterruptAfterFailInInterruptingRule() throws InvalidPasswordDataException {
        prepareRule(minLengthRule, true, true, passwordData);
        prepareRule(maxLengthRule, false, true, passwordData);
        prepareRule(uppercaseRule, false, true, passwordData);
        prepareRule(digitsRule, false, true, passwordData);

        SimpleValidationResult result = passwordValidator.validateErrors(passwordData);
        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        assertEquals(PasswordError.TOO_LONG, result.getErrors().get(0));

        prepareRule(minLengthRule, true, true, passwordData);
        prepareRule(maxLengthRule, false, false, passwordData);
        prepareRule(uppercaseRule, false, true, passwordData);
        prepareRule(digitsRule, false, true, passwordData);

        SimpleValidationResult result2 = passwordValidator.validateErrors(passwordData);
        assertFalse(result2.isValid());
        assertEquals(2, result2.getErrors().size());
        assertEquals(PasswordError.TOO_LONG, result2.getErrors().get(0));
        assertEquals(PasswordError.UPPERCASE, result2.getErrors().get(1));
    }

    private void prepareRule(Rule rule,boolean result, boolean interrupting,  PasswordData password) throws InvalidPasswordDataException {
        when(rule.validateSimple(password)).thenReturn(result);
        when(rule.isInterrupting()).thenReturn(interrupting);
    }

}
