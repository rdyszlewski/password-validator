package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.PasswordError;
import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;
import com.farfocle.password_validator.test_utils.TestExceptionUtils;
import org.junit.Test;

import static com.farfocle.password_validator.test_utils.TestUtils.testPasswordFail;
import static com.farfocle.password_validator.test_utils.TestUtils.testPasswordSuccess;
import static org.junit.Assert.assertEquals;

public class MinLengthRuleTest {

    @Test
    public void shouldReturnFalseWhenPasswordTooShort() throws InvalidPasswordDataException {
        Rule rule = createRule(5);
        testPasswordFail("", rule);
        testPasswordFail("a", rule);
        testPasswordFail("aaaa", rule);
    }

    private MinLengthRule createRule(int value){
        return new MinLengthRule.Builder(value).build();
    }

    @Test
    public void shouldReturnTrueWhenPasswordIsCorrect() throws InvalidPasswordDataException {
        Rule rule = createRule(5);
        testPasswordSuccess("aaaaa", rule);
        testPasswordSuccess("aaaaaaaaaaaaaa", rule);
        testPasswordSuccess("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", rule);
    }

    @Test
    public void shouldThrowNullPasswordException() {
        Rule rule = createRule(5);
        TestExceptionUtils.testInvalidPasswordDataException(rule);

    }

    @Test
    public void shouldReturnTrueWhenNotCharacters() throws InvalidPasswordDataException {
        Rule rule = createRule(5);
        testPasswordSuccess("#$%#$%", rule);
        testPasswordSuccess("śćśśśść", rule);
        testPasswordSuccess("1123131", rule);
        testPasswordSuccess("1#śPo5", rule);
    }

    @Test
    public void shouldReturnCorrectErrorDetails() {
        Rule rule = createRule(5);
        assertEquals(PasswordError.TOO_SHORT, rule.getErrorType());
    }

}
