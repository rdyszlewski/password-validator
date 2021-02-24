package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.PasswordData;
import com.farfocle.password_validator.PasswordError;
import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;
import com.farfocle.password_validator.test_utils.TestExceptionUtils;
import org.junit.Test;

import static com.farfocle.password_validator.test_utils.TestUtils.testException;
import static org.junit.Assert.*;

public class UsernameRuleTest {

    @Test
    public void shouldReturnFalseWhenUsernameAndPasswordAreTheSame() throws InvalidPasswordDataException {
        Rule rule = createRule();
        PasswordData passwordData = new PasswordData("Antek");
        passwordData.setUsername("Antek");
        assertFalse(rule.validate(passwordData).isValid());
    }

    private UsernameRule createRule() {
        return new UsernameRule.Builder().build();
    }

    @Test
    public void shouldReturnFalseWhenUsernameAndPasswordAreDifferent() throws InvalidPasswordDataException {
        Rule rule = createRule();
        PasswordData passwordData = new PasswordData("Antek");
        passwordData.setUsername("Kaziu");
        assertTrue(rule.validate(passwordData).isValid());
    }

    // TODO: zrobić test, który testuje wyrzucenie błędu, jeżeli username nie jest podany
    @Test
    public void shouldReturnCorrectErrorDetails() {
        Rule rule = createRule();
        assertEquals(PasswordError.USERNAME, rule.getErrorType());
    }

    @Test
    public void shouldThrowExceptionWhenDataIsEmpty() {
        Rule rule = createRule();
        TestExceptionUtils.testInvalidPasswordDataException(rule);
        PasswordData passwordData = new PasswordData("sa", null);
        testException(passwordData, InvalidPasswordDataException.class, rule);
        try {
            rule.validate(passwordData);
        } catch (InvalidPasswordDataException e) {
            assertEquals(InvalidPasswordDataException.Type.USERNAME_NULL, e.getErrorType());
        }
    }
}
