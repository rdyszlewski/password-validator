package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.PasswordError;
import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;
import com.farfocle.password_validator.test_utils.TestExceptionUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.farfocle.password_validator.test_utils.TestUtils.*;
import static org.junit.Assert.assertEquals;

public class SpecialCharactersRuleTest {

    @Test
    public void shouldReturnFalseWhenNotEnoughSpecialCharacters() throws InvalidPasswordDataException {
        Rule rule = createRule(2);
        List<String> passwords = Arrays.asList(
                "aaaa",
                "aa}aa",
                "[aaaa",
                "aaaa-"
        );
        testFailAll(passwords, rule);
    }

    private SpecialCharactersRule createRule(int value) {
        return new SpecialCharactersRule.Builder(value).build();
    }

    @Test
    public void shouldReturnTrueWhenEnoughSpecialCharacters() throws InvalidPasswordDataException {
        Rule rule = createRule(2);
        List<String> passwords = Arrays.asList(
                "//ddd#)(",
                "#_.,()",
                "a#a[a"
        );
        testSuccessAll(passwords, rule);
    }

    @Test
    public void shouldThrowNullPasswordException() {
        Rule rule = createRule(2);
        TestExceptionUtils.testInvalidPasswordDataException(rule);

    }

    @Test
    public void shouldReturnTrueWhenGetSpecialsFromList() throws InvalidPasswordDataException {
        List<Character> list = Arrays.asList('#', '/', '_');
        Rule rule = new SpecialCharactersRule.Builder(2).setCharacters(list).build();

        testPasswordSuccess("das#das_", rule);
        testPasswordFail("das#dj-", rule);
    }

    @Test
    public void shouldReturnCorrectErrorDetails() {
        Rule rule = createRule(2);
        assertEquals(PasswordError.SPECIAL_CHARACTERS, rule.getErrorType());
    }
}
