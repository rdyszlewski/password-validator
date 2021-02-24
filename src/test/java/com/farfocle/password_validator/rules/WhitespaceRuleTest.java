package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.PasswordData;
import com.farfocle.password_validator.PasswordError;
import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;
import com.farfocle.password_validator.test_utils.TestExceptionUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.farfocle.password_validator.test_utils.TestUtils.testFailAll;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WhitespaceRuleTest {

    @Test
    public void shouldReturnFalseWhenWhitespace() throws InvalidPasswordDataException {
        Rule rule = createRule();
        List<String> passwords = Arrays.asList(
                "jdkla dajl",
                " sadjklas",
                "das\tdas",
                "das\nsada"
        );
        testFailAll(passwords, rule);

        // TODO: dodać inne znaki
    }

    private WhitespaceRule createRule() {
        return new WhitespaceRule.Builder().build();
    }

    @Test
    public void shouldReturnTrueWhenNoWhitespaces() throws InvalidPasswordDataException {
        Rule rule = createRule();
        PasswordData passwordData = new PasswordData("asdjkladh25eqwuwen");
        assertTrue(rule.validatePassword(passwordData).isValid());
    }

    @Test
    public void shouldThrowNullPasswordException() {
        Rule rule = createRule();
        TestExceptionUtils.testInvalidPasswordDataException(rule);
    }

    @Test
    public void shouldReturnCorrectErrorDetails() {
        Rule rule = createRule();
        assertEquals(PasswordError.WHITESPACE, rule.getErrorType());
    }
}
