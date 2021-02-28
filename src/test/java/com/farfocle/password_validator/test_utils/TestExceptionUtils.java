package com.farfocle.password_validator.test_utils;

import com.farfocle.password_validator.models.PasswordData;
import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;
import com.farfocle.password_validator.rules.Rule;

import static com.farfocle.password_validator.test_utils.TestUtils.testException;
import static org.junit.Assert.assertEquals;

public class TestExceptionUtils {

    public static void testInvalidPasswordDataException(Rule rule) {
        testException(null, InvalidPasswordDataException.class, rule);
        try {
            rule.validate(null);
        } catch (InvalidPasswordDataException e) {
            assertEquals(InvalidPasswordDataException.Type.DATA_NULL, e.getErrorType());
        }

        PasswordData passwordData = new PasswordData(null, null);
        testException(passwordData, InvalidPasswordDataException.class, rule);

        try {
            rule.validate(passwordData);
        } catch (InvalidPasswordDataException e) {
            assertEquals(InvalidPasswordDataException.Type.PASSWORD_NULL, e.getErrorType());
        }
    }
}
