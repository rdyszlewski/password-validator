package com.farfocle.password_validator.test_utils;

import com.farfocle.password_validator.models.PasswordData;
import com.farfocle.password_validator.models.PasswordRuleResult;
import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;
import com.farfocle.password_validator.rules.Rule;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class TestUtils {

    public static void testPasswordSuccess(String password, Rule rule) throws InvalidPasswordDataException {
        PasswordData passwordData = new PasswordData(password);
        PasswordRuleResult result = rule.validate(passwordData);
        assertThat(result.isValid()).as(password).isTrue();
        assertThat(result.getErrorType()).as(password).isNull();
        assertThat(result.getErrorInfo()).as(password).isNull();
    }

    public static void testSuccessAll(List<String> passwords, Rule rule) throws InvalidPasswordDataException {
        for (String password : passwords) {
            testPasswordSuccess(password, rule);
        }
    }

    public static void testPasswordFail(String password, Rule rule) throws InvalidPasswordDataException {
        PasswordData passwordData = new PasswordData(password);
        assertThat(rule.validate(passwordData).isValid()).as(password).isFalse();
        // TODO: tutaj dodać testy, czy zawiera odpowiednie elementy
    }

    public static void testFailAll(List<String> passwords, Rule rule) throws InvalidPasswordDataException {
        for (String password : passwords) {
            testPasswordFail(password, rule);
        }
    }

    public static void testException(PasswordData data, Class<?> type, Rule rule) {
        assertThatThrownBy(() -> rule.validate(data)).isInstanceOf(type);
    }
}
