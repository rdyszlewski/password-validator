package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.PasswordError;
import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;
import com.farfocle.password_validator.test_utils.TestExceptionUtils;
import org.junit.Test;

import static com.farfocle.password_validator.test_utils.TestUtils.testPasswordFail;
import static com.farfocle.password_validator.test_utils.TestUtils.testPasswordSuccess;
import static org.junit.Assert.assertEquals;

public class UppercaseRuleTest {

    // TODO: dodać testy z przyjmowanymi znakami

    @Test
    public void shouldReturnFalseWhenNotEnoughUppercase() throws InvalidPasswordDataException {
        Rule rule = createRule(2);
        testPasswordFail("aaaa", rule);
        testPasswordFail("aaAaa", rule);
        testPasswordFail("Aaaaa", rule);
        testPasswordFail("aaaaZ", rule);
    }

    private UppercaseRule createRule(int value){
        return new UppercaseRule.Builder(value).build();
    }

    @Test
    public void shouldReturnTrueWhenEnoughUppercase() throws InvalidPasswordDataException {
        Rule rule = createRule(2);
        testPasswordSuccess("ACdddADP", rule);
        testPasswordSuccess("AADJKJDLAJD", rule);
        testPasswordSuccess("aAaAa", rule);
    }

    @Test
    public void shouldThrowNullPasswordException() {
        Rule rule = createRule(2);
        TestExceptionUtils.testInvalidPasswordDataException(rule);

    }

    @Test
    public void shouldReturnTrueWhenNationalCharacters() throws InvalidPasswordDataException {
        Rule rule = createRule(2);
        testPasswordSuccess("kŚoĆte", rule);
        testPasswordSuccess("kjiĘOs", rule);
        testPasswordSuccess("ÖOppppp", rule);
    }

    @Test
    public void shouldReturnCorrectErrorDetails() {
        Rule rule = createRule(2);
        assertEquals(PasswordError.UPPERCASE, rule.getErrorType());
    }
}
