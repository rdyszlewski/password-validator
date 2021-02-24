package com.farfocle.password_validator;

import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;
import com.farfocle.password_validator.rules.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class PasswordValidationSimpleTest {

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

        assertTrue(passwordValidator.validateSimple(passwordData));
    }

    @Test
    public void shouldReturnFalseWhenAnyRuleIsFail() throws InvalidPasswordDataException {
        prepareRule(minLengthRule, false, false, passwordData);
        prepareRule(maxLengthRule, true, false, passwordData);
        prepareRule(uppercaseRule, true, false, passwordData);
        prepareRule(digitsRule, true, false, passwordData);

        assertFalse(passwordValidator.validateSimple(passwordData));

        prepareRule(minLengthRule, true, false, passwordData);
        prepareRule(maxLengthRule, false, false, passwordData);
        prepareRule(uppercaseRule, true, false, passwordData);
        prepareRule(digitsRule, true, false, passwordData);

        assertFalse(passwordValidator.validateSimple(passwordData));

        prepareRule(minLengthRule, true, false, passwordData);
        prepareRule(maxLengthRule, true, false, passwordData);
        prepareRule(uppercaseRule, false, false, passwordData);
        prepareRule(digitsRule, true, false, passwordData);

        assertFalse(passwordValidator.validateSimple(passwordData));

        prepareRule(minLengthRule, true, false, passwordData);
        prepareRule(maxLengthRule, true, false, passwordData);
        prepareRule(uppercaseRule, true, false, passwordData);
        prepareRule(digitsRule, false, false, passwordData);

        assertFalse(passwordValidator.validateSimple(passwordData));

        prepareRule(minLengthRule, false, false, passwordData);
        prepareRule(maxLengthRule, false, false, passwordData);
        prepareRule(uppercaseRule, false, false, passwordData);
        prepareRule(digitsRule, false, false, passwordData);

        assertFalse(passwordValidator.validateSimple(passwordData));
    }

    // TODO: można dodać jakieś testy, sprawdzające, ile z tych zasad się wykonuje

    private void prepareRule(Rule rule,boolean result, boolean interrupting,  PasswordData password) throws InvalidPasswordDataException {
        when(rule.validateSimple(password)).thenReturn(result);
        when(rule.isInterrupting()).thenReturn(interrupting);
    }
}
