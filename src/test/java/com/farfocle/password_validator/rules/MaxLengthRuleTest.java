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

public class MaxLengthRuleTest {

    @Test
    public void shouldReturnFalseWhenPasswordTooLong() throws InvalidPasswordDataException {
        Rule rule = createRule(5);
        List<String> passwords = Arrays.asList(
                "aadadadaasfsdfsdjkflasdflasdjksfasfs",
                "aaaaaa"
        );
        testFailAll(passwords, rule);
    }

    private MaxLengthRule createRule(int value) {
        return new MaxLengthRule.Builder(value).build();
    }

    @Test
    public void shouldReturnTrueWhenPasswordCorrect() throws InvalidPasswordDataException {
        Rule rule = createRule(5);
        List<String> passwords = Arrays.asList(
                "",
                "aaa",
                "aaaaa"
        );
        testSuccessAll(passwords, rule);
    }

    @Test
    public void shouldThrowExceptionWhenPasswordIsNull() {
        Rule rule = createRule(5);
        TestExceptionUtils.testInvalidPasswordDataException(rule);
    }

    @Test
    public void shouldReturnTrueWhenNotCharacters() throws InvalidPasswordDataException {
        Rule rule = createRule(8);
        List<String> passwords = Arrays.asList(
                "#$%#$%",
                "śćśśśść",
                "1123131",
                "1#śPo5>"
        );
        testSuccessAll(passwords, rule);
    }

    @Test
    public void shouldReturnCorrectErrorDetails() {
        Rule rule = createRule(8);
        assertEquals(PasswordError.TOO_LONG, rule.getErrorType());
        // TODO: sprawdzić parametry
    }
}
