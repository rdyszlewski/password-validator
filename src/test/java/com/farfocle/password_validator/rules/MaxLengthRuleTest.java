package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.PasswordError;
import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;
import com.farfocle.password_validator.test_utils.TestExceptionUtils;
import org.junit.Test;

import static com.farfocle.password_validator.test_utils.TestUtils.testPasswordFail;
import static com.farfocle.password_validator.test_utils.TestUtils.testPasswordSuccess;
import static org.junit.Assert.assertEquals;

public class MaxLengthRuleTest {

    @Test
    public void shouldReturnFalseWhenPasswordTooLong() throws InvalidPasswordDataException {
        Rule rule = createRule(5);
        testPasswordFail("aadadadaasfsdfsdjkflasdflasdjksfasfs", rule);
        testPasswordFail("aaaaaa", rule);
    }

    private MaxLengthRule createRule(int value){
        return new MaxLengthRule.Builder(value).build();
    }

    @Test
    public void shouldReturnTrueWhenPasswordCorrect() throws InvalidPasswordDataException {
        Rule rule = createRule(5);
        testPasswordSuccess("", rule);
        testPasswordSuccess("aaa", rule);
        testPasswordSuccess("aaaaa", rule);
    }

    @Test
    public void shouldThrowExceptionWhenPasswordIsNull() {
        Rule rule = createRule(5);
        TestExceptionUtils.testInvalidPasswordDataException(rule);
    }

    @Test
    public void shouldReturnTrueWhenNotCharacters() throws InvalidPasswordDataException {
        Rule rule = createRule(8);
        testPasswordSuccess("#$%#$%", rule);
        testPasswordSuccess("śćśśśść", rule);
        testPasswordSuccess("1123131", rule);
        testPasswordSuccess("1#śPo5>", rule);
    }

    @Test
    public void shouldReturnCorrectErrorDetails() {
        Rule rule = createRule(8);
        assertEquals(PasswordError.TOO_LONG, rule.getErrorType());
        // TODO: sprawdzić parametry
    }
}
