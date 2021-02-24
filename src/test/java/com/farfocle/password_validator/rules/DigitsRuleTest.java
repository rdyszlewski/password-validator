package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.PasswordError;
import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;
import com.farfocle.password_validator.test_utils.TestExceptionUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.farfocle.password_validator.test_utils.TestUtils.testFailAll;
import static com.farfocle.password_validator.test_utils.TestUtils.testSuccessAll;
import static org.junit.Assert.*;

public class DigitsRuleTest {

    @Test
    public void shouldReturnFalseWhenNotEnoughDigits() throws InvalidPasswordDataException {
        Rule rule = createRule(2);
        List<String> passwords = Arrays.asList(
                "aaaa",
                "aa4aa",
                "2aaaa",
                "aaaa4"
        );
        testFailAll(passwords, rule);
    }

    private DigitsRule createRule(int value) {
        return new DigitsRule.Builder(value).build();
    }

    @Test
    public void shouldReturnTrueWhenEnoughDigits() throws InvalidPasswordDataException {
        Rule rule = createRule(2);
        List<String> passwords = Arrays.asList(
                "13ddd543",
                "3948392",
                "a4a4a"

        );
        testSuccessAll(passwords, rule);
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
