package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.PasswordData;
import com.farfocle.password_validator.PasswordError;
import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;
import com.farfocle.password_validator.test_utils.TestExceptionUtils;
import org.junit.Test;

import static com.farfocle.password_validator.test_utils.TestUtils.testPasswordFail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WhitespaceRuleTest {

    @Test
    public void shouldReturnFalseWhenWhitespace() throws InvalidPasswordDataException {
        Rule rule = createRule();
        testPasswordFail("jdkla dajl", rule);
        testPasswordFail(" sadjklas", rule);
        testPasswordFail("das\tdas", rule);
        testPasswordFail("das\nsada", rule);

        // TODO: dodać inne znaki
    }

    private WhitespaceRule createRule(){
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
