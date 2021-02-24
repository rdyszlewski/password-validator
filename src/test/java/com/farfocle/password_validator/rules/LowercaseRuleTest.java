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

public class LowercaseRuleTest {

    // TODO: dodać testy z przyjmowanymi znakami

    @Test
    public void shouldReturnFalseWhenNotEnoughLowercase() throws InvalidPasswordDataException {
        Rule rule = createRule(2);
        List<String> passwords = Arrays.asList(
                "AAAA",
                "AAaAA",
                "aAAAA",
                "AAAAa"
        );
        testFailAll(passwords, rule);
    }

    private Rule createRule(int value) {
        return new LowercaseRule.Builder(value).build();
    }

    @Test
    public void shouldReturnTrueWhenEnoughLowercase() throws InvalidPasswordDataException {
        Rule rule = createRule(2);
        List<String> passwords = Arrays.asList(
                "AaaAaa",
                "aaaaaa",
                "AaAaA"
        );
        testSuccessAll(passwords, rule);
    }

    @Test
    public void shouldThrowNullPasswordException() {
        Rule rule = createRule(2);
        TestExceptionUtils.testInvalidPasswordDataException(rule);
    }

    @Test
    public void shouldReturnCorrectErrorDetails() {
        Rule rule = createRule(2);
        assertEquals(PasswordError.LOWERCASE, rule.getErrorType());

    }

    @Test
    public void shouldBuildRule() {
        // TODO: przepisać to do jednej metody
        LowercaseRule rule = new LowercaseRule.Builder(2).setInterrupting().build();
        assertTrue(rule.isInterrupting());
        assertEquals(2, rule.getValue());

        LowercaseRule rule2 = new LowercaseRule.Builder(2).setInterrupting(true).build();
        assertTrue(rule2.isInterrupting());

        LowercaseRule rule3 = new LowercaseRule.Builder(2).setInterrupting(false).build();
        assertFalse(rule3.isInterrupting());
    }
}
