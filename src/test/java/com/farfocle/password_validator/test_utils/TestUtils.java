package com.farfocle.password_validator.test_utils;

import com.farfocle.password_validator.PasswordData;
import com.farfocle.password_validator.rules.Rule;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestUtils {

    public static void testPasswordSuccess(String password, Rule rule) {
        PasswordData longPassword = new PasswordData(password);
        assertTrue(rule.validate(longPassword).isValid());
    }

    public static void testPasswordFail(String password, Rule rule) {
        PasswordData almostPassword = new PasswordData(password);
        assertFalse(rule.validate(almostPassword).isValid());
    }

    public static void testException(PasswordData data, Class<?> type, Rule rule) {
        assertThatThrownBy(() -> rule.validate(data)).isInstanceOf(type);
    }
}
