package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.PasswordData;
import com.farfocle.password_validator.PasswordError;
import org.junit.Test;

import static com.farfocle.password_validator.test_utils.TestUtils.*;
import static org.junit.Assert.assertEquals;

public class UppercaseRuleTest {

    // TODO: dodać testy z przyjmowanymi znakami

    @Test
    public void shouldReturnFalseWhenNotEnoughUppercase() {
//        UppercaseRule rule = new UppercaseRule(2);
        Rule rule = new UppercaseRule.Builder(2).build();
        testPasswordFail("aaaa", rule);
        testPasswordFail("aaAaa", rule);
        testPasswordFail("Aaaaa", rule);
        testPasswordFail("aaaaZ", rule);
    }

    @Test
    public void shouldReturnTrueWhenEnoughUppercase() {
        Rule rule = new UppercaseRule.Builder(2).build();
        testPasswordSuccess("ACdddADP", rule);
        testPasswordSuccess("AADJKJDLAJD", rule);
        testPasswordSuccess("aAaAa", rule);
    }

    @Test
    public void shouldThrowNullPasswordException() {
        Rule rule = new UppercaseRule.Builder(2).build();
        testException(null, NullPointerException.class, rule);

        PasswordData nullPassword = new PasswordData(null);
        testException(nullPassword, NullPointerException.class, rule);
    }

    @Test
    public void shouldReturnTrueWhenNationalCharacters() {
        Rule rule = new UppercaseRule.Builder(2).build();
        testPasswordSuccess("kŚoĆte", rule);
        testPasswordSuccess("kjiĘOs", rule);
        testPasswordSuccess("ÖOppppp", rule);
    }

    @Test
    public void shouldReturnCorrectErrorDetails() {
        Rule rule = new UppercaseRule.Builder(2).build();
        assertEquals(PasswordError.UPPERCASE, rule.getErrorType());
    }
}
