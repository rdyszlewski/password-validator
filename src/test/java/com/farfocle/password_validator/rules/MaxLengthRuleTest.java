package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.PasswordData;
import com.farfocle.password_validator.PasswordError;
import org.junit.Test;

import static com.farfocle.password_validator.test_utils.TestUtils.*;
import static org.junit.Assert.assertEquals;

public class MaxLengthRuleTest {

    @Test
    public void shouldReturnFalseWhenPasswordTooLong() {
        Rule rule = new MaxLengthRule.Builder(5).build();
        testPasswordFail("aadadadaasfsdfsdjkflasdflasdjksfasfs", rule);
        testPasswordFail("aaaaaa", rule);
    }

    @Test
    public void shouldReturnTrueWhenPasswordCorrect() {
        Rule rule = new MaxLengthRule.Builder(5).build();
        testPasswordSuccess("", rule);
        testPasswordSuccess("aaa", rule);
        testPasswordSuccess("aaaaa", rule);
    }

    @Test
    public void shouldThrowExceptionWhenPasswordIsNull() {
        Rule rule = new MaxLengthRule.Builder(5).build();
        testException(null, NullPointerException.class, rule);

        PasswordData nullPassword = new PasswordData(null);
        testException(nullPassword, NullPointerException.class, rule);
    }

    @Test
    public void shouldReturnTrueWhenNotCharacters() {
        Rule rule = new MaxLengthRule.Builder(8).build();
        testPasswordSuccess("#$%#$%", rule);
        testPasswordSuccess("śćśśśść", rule);
        testPasswordSuccess("1123131", rule);
        testPasswordSuccess("1#śPo5>", rule);
    }

    @Test
    public void shouldReturnCorrectErrorDetails() {
        Rule rule = new MaxLengthRule.Builder(8).build();
        assertEquals(PasswordError.TOO_LONG, rule.getErrorType());
        // TODO: sprawdzić parametry
    }
}
