package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.PasswordData;
import com.farfocle.password_validator.PasswordError;
import org.junit.Test;

import static com.farfocle.password_validator.test_utils.TestUtils.*;
import static org.junit.Assert.*;

public class LowercaseRuleTest {

    // TODO: dodać testy z przyjmowanymi znakami

    @Test
    public void shouldReturnFalseWhenNotEnoughLowercase() {
//        Rule rule = new LowercaseRule(2);
        Rule rule = new LowercaseRule.Builder(2).build();
        testPasswordFail("AAAA", rule);
        testPasswordFail("AAaAA", rule);
        testPasswordFail("aAAAA", rule);
        testPasswordFail("AAAAa", rule);
    }

    @Test
    public void shouldReturnTrueWhenEnoughLowercase() {
        Rule rule = new LowercaseRule.Builder(2).build();
        testPasswordSuccess("AaaAaa", rule);
        testPasswordSuccess("aaaaaa", rule);
        testPasswordSuccess("AaAaA", rule);
    }

    @Test
    public void shouldThrowNullPasswordException() {
        Rule rule = new LowercaseRule.Builder(2).build();
        testException(null, NullPointerException.class, rule);

        PasswordData nullPassword = new PasswordData(null);
        testException(nullPassword, NullPointerException.class, rule);
    }

    @Test
    public void shouldReturnCorrectErrorDetails() {
        Rule rule = new LowercaseRule.Builder(2).build();
        assertEquals(PasswordError.LOWERCASE, rule.getErrorType());

    }

    @Test
    public void shouldBuildRule(){
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
