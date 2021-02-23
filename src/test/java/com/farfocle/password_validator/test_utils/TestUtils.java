package com.farfocle.password_validator.test_utils;

import com.farfocle.password_validator.PasswordData;
import com.farfocle.password_validator.PasswordRuleResult;
import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;
import com.farfocle.password_validator.rules.Rule;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.*;

public class TestUtils {

    public static void testPasswordSuccess(String password, Rule rule) throws InvalidPasswordDataException {
        PasswordData passwordData = new PasswordData(password);
        PasswordRuleResult result = rule.validate(passwordData);
        assertTrue(result.isValid());
        assertNull(result.getErrorType());
        assertNull(result.getErrorInfo());
    }

    public static void testPasswordFail(String password, Rule rule) throws InvalidPasswordDataException {
        PasswordData almostPassword = new PasswordData(password);
        assertFalse(rule.validate(almostPassword).isValid());
        // TODO: tutaj dodać testy, czy zawiera odpowiednie elementy
    }

    public static void testException(PasswordData data, Class<?> type, Rule rule) {
        assertThatThrownBy(() -> rule.validate(data)).isInstanceOf(type);
    }
}
