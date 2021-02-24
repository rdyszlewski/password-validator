package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.PasswordError;
import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;
import com.farfocle.password_validator.test_utils.TestExceptionUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.farfocle.password_validator.test_utils.TestUtils.testFailAll;
import static com.farfocle.password_validator.test_utils.TestUtils.testSuccessAll;
import static org.junit.Assert.assertEquals;

public class MinLengthRuleTest {

    @Test
    public void shouldReturnFalseWhenPasswordTooShort() throws InvalidPasswordDataException {
        Rule rule = createRule(5);
        List<String> passwords = Arrays.asList(
                "",
                "a",
                "aaaa"
        );
        testFailAll(passwords, rule);
    }

    private MinLengthRule createRule(int value) {
        return new MinLengthRule.Builder(value).build();
    }

    @Test
    public void shouldReturnTrueWhenPasswordIsCorrect() throws InvalidPasswordDataException {
        Rule rule = createRule(5);
        List<String> passwords = Arrays.asList(
                "aaaaa",
                "aaaaaaaaaaaaaa",
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        );
        testSuccessAll(passwords, rule);
    }

    @Test
    public void shouldThrowNullPasswordException() {
        Rule rule = createRule(5);
        TestExceptionUtils.testInvalidPasswordDataException(rule);

    }

    @Test
    public void shouldReturnTrueWhenNotCharacters() throws InvalidPasswordDataException {
        Rule rule = createRule(5);
        List<String> passwords = Arrays.asList(
                "#$%#$%",
                "śćśśśść",
                "1123131",
                "1#śPo5"
        );
        testSuccessAll(passwords, rule);
    }

    @Test
    public void shouldReturnCorrectErrorDetails() {
        Rule rule = createRule(5);
        assertEquals(PasswordError.TOO_SHORT, rule.getErrorType());
    }

}
