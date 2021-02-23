package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.PasswordData;
import com.farfocle.password_validator.PasswordError;
import com.farfocle.password_validator.test_utils.TestUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DigitsRuleTest {
    @Test
    public void shouldReturnFalseWhenNotEnoughDigits() {
        Rule rule = new DigitsRule.Builder(2).build();
        TestUtils.testPasswordFail("aaaa", rule);
        TestUtils.testPasswordFail("aa4aa", rule);
        TestUtils.testPasswordFail("2aaaa", rule);
        TestUtils.testPasswordFail("aaaa4", rule);

    }

    @Test
    public void shouldReturnTrueWhenEnoughDigits() {
        Rule rule = new DigitsRule.Builder(2).build();
        TestUtils.testPasswordSuccess("13ddd543", rule);
        TestUtils.testPasswordSuccess("3948392", rule);
        TestUtils.testPasswordSuccess("a4a4a", rule);
    }

    @Test
    public void shouldThrowNullPasswordException() {
        Rule rule = new DigitsRule.Builder(2).build();
        TestUtils.testException(null, NullPointerException.class, rule);

        PasswordData nullPassword = new PasswordData(null);
        TestUtils.testException(nullPassword, NullPointerException.class, rule);
    }

    @Test
    public void shouldReturnDigitError() {
        Rule rule = new DigitsRule.Builder(2).build();
        assertEquals(PasswordError.DIGITS, rule.getErrorType());
    }
}
