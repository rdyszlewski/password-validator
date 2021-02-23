package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.PasswordError;
import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;
import com.farfocle.password_validator.test_utils.TestExceptionUtils;
import org.junit.Test;

import static com.farfocle.password_validator.test_utils.TestUtils.testPasswordFail;
import static com.farfocle.password_validator.test_utils.TestUtils.testPasswordSuccess;
import static org.junit.Assert.*;

public class LowercaseRuleTest {

    // TODO: dodać testy z przyjmowanymi znakami

    @Test
    public void shouldReturnFalseWhenNotEnoughLowercase() throws InvalidPasswordDataException {
        Rule rule = createRule(2);
        testPasswordFail("AAAA", rule);
        testPasswordFail("AAaAA", rule);
        testPasswordFail("aAAAA", rule);
        testPasswordFail("AAAAa", rule);
    }

    private Rule createRule(int value){
        return new LowercaseRule.Builder(value).build();
    }

    @Test
    public void shouldReturnTrueWhenEnoughLowercase() throws InvalidPasswordDataException {
        Rule rule = createRule(2);
        testPasswordSuccess("AaaAaa", rule);
        testPasswordSuccess("aaaaaa", rule);
        testPasswordSuccess("AaAaA", rule);
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
