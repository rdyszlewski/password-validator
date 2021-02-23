package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.PasswordError;
import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;
import com.farfocle.password_validator.test_utils.TestExceptionUtils;
import org.junit.Test;

import static com.farfocle.password_validator.test_utils.TestUtils.testPasswordFail;
import static com.farfocle.password_validator.test_utils.TestUtils.testPasswordSuccess;
import static org.junit.Assert.*;

public class DigitsRuleTest {

    @Test
    public void shouldReturnFalseWhenNotEnoughDigits() throws InvalidPasswordDataException {
        Rule rule = createRule(2);
        testPasswordFail("aaaa", rule);
        testPasswordFail("aa4aa", rule);
        testPasswordFail("2aaaa", rule);
        testPasswordFail("aaaa4", rule);
    }

    private DigitsRule createRule(int value){
        return new DigitsRule.Builder(value).build();
    }

    @Test
    public void shouldReturnTrueWhenEnoughDigits() throws InvalidPasswordDataException {
        Rule rule = createRule(2);
        testPasswordSuccess("13ddd543", rule);
        testPasswordSuccess("3948392", rule);
        testPasswordSuccess("a4a4a", rule);
    }

    @Test
    public void shouldThrowNullPasswordException() {
        Rule rule = createRule(2);
        TestExceptionUtils.testInvalidPasswordDataException(rule);
    }

    @Test
    public void shouldReturnDigitError() {
        Rule rule = createRule(2);
        assertEquals(PasswordError.DIGITS, rule.getErrorType());
    }

    @Test
    public void shouldBuildValidObject() {
        DigitsRule rule = new DigitsRule.Builder(3).setInterrupting().build();
        assertTrue(rule.isInterrupting());
        assertEquals(3, rule.getValue());

        DigitsRule rule2 = new DigitsRule.Builder(2).setInterrupting(true).build();
        assertTrue(rule2.isInterrupting());

        DigitsRule rule3 = new DigitsRule.Builder(2).setInterrupting(false).build();
        assertFalse(rule3.isInterrupting());
    }
}
