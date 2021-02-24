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

public class UppercaseRuleTest {

    // TODO: dodać testy z przyjmowanymi znakami

    @Test
    public void shouldReturnFalseWhenNotEnoughUppercase() throws InvalidPasswordDataException {
        Rule rule = createRule(2);
        List<String> passwords = Arrays.asList(
                "aaaa",
                "aaAaa",
                "Aaaaa",
                "aaaaZ"
        );
        testFailAll(passwords, rule);
    }

    private UppercaseRule createRule(int value) {
        return new UppercaseRule.Builder(value).build();
    }

    @Test
    public void shouldReturnTrueWhenEnoughUppercase() throws InvalidPasswordDataException {
        Rule rule = createRule(2);
        List<String> passwords = Arrays.asList(
                "ACdddADP",
                "AADJKJDLAJD",
                "aAaAa"
        );
        testSuccessAll(passwords, rule);
    }

    @Test
    public void shouldThrowNullPasswordException() {
        Rule rule = createRule(2);
        TestExceptionUtils.testInvalidPasswordDataException(rule);

    }

    @Test
    public void shouldReturnTrueWhenNationalCharacters() throws InvalidPasswordDataException {
        Rule rule = createRule(2);
        List<String> passwords = Arrays.asList(
                "kŚoĆte",
                "kjiĘOs",
                "ÖOppppp"
        );
        testSuccessAll(passwords, rule);
    }

    @Test
    public void shouldReturnCorrectErrorDetails() {
        Rule rule = createRule(2);
        assertEquals(PasswordError.UPPERCASE, rule.getErrorType());
    }
}
