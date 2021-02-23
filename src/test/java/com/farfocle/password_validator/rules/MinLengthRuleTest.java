package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.PasswordData;
import com.farfocle.password_validator.PasswordError;
import org.junit.Test;

import static com.farfocle.password_validator.test_utils.TestUtils.*;
import static org.junit.Assert.assertEquals;

public class MinLengthRuleTest {

    @Test
    public void shouldReturnFalseWhenPasswordTooShort() {
        Rule rule = new MinLengthRule.Builder(5).build();
        testPasswordFail("", rule);
        testPasswordFail("a", rule);
        testPasswordFail("aaaa", rule);
    }

    @Test
    public void shouldReturnTrueWhenPasswordIsCorrect() {
        Rule rule = new MinLengthRule.Builder(5).build();
        testPasswordSuccess("aaaaa", rule);
        testPasswordSuccess("aaaaaaaaaaaaaa", rule);
        testPasswordSuccess("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", rule);
    }

    @Test
    public void shouldThrowNullPasswordException() {
        Rule rule = new MinLengthRule.Builder(5).build();
        testException(null, NullPointerException.class, rule);

        PasswordData nullPassword = new PasswordData(null);
        testException(nullPassword, NullPointerException.class, rule);
    }

    @Test
    public void shouldReturnTrueWhenNotCharacters() {
        Rule rule = new MinLengthRule.Builder(5).build();
        testPasswordSuccess("#$%#$%", rule);
        testPasswordSuccess("śćśśśść", rule);
        testPasswordSuccess("1123131", rule);
        testPasswordSuccess("1#śPo5", rule);
    }

    @Test
    public void shouldReturnCorrectErrorDetails() {
        Rule rule = new MinLengthRule.Builder(5).build();
        assertEquals(PasswordError.TOO_SHORT, rule.getErrorType());
    }

}
