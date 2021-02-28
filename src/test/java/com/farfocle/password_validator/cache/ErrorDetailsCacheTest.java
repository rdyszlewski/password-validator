package com.farfocle.password_validator.cache;

import com.farfocle.password_validator.models.ErrorDetails;
import com.farfocle.password_validator.models.PasswordError;
import com.farfocle.password_validator.models.PasswordRuleResult;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class ErrorDetailsCacheTest {

    @Test
    public void shouldWork(){
        ErrorDetailsCache cache = new ErrorDetailsCache(2);

        PasswordRuleResult result1 = PasswordRuleResult.createFail(PasswordError.TOO_SHORT, new ConcurrentHashMap<>());
        PasswordRuleResult result2 = PasswordRuleResult.createFail(PasswordError.USERNAME, new ConcurrentHashMap<>());
        PasswordRuleResult result3 = PasswordRuleResult.createFail(PasswordError.SPECIAL_CHARACTERS, new ConcurrentHashMap<>());
        CreateErrorDetailsFunction function = r->new ErrorDetails(r.getErrorType(), r.getErrorInfo(), null);
        ErrorDetails errorDetails1 = cache.getErrorDetails(result1, function);
        ErrorDetails errorDetails2 = cache.getErrorDetails(result2, function);
        assertEquals(result1.getErrorType(), errorDetails1.getErrorType());
        assertEquals(result2.getErrorType(), errorDetails2.getErrorType());
        assertEquals(2, cache.getSize());
        assertTrue(cache.isPresent(result1));
        assertTrue(cache.isPresent(result2));
        ErrorDetails errorDetails3 = cache.getErrorDetails(result3, function);
        assertEquals(result3.getErrorType(), errorDetails3.getErrorType());
        assertEquals(2, cache.getSize());
        assertTrue(cache.isPresent(result2));
        assertTrue(cache.isPresent(result3));

    }
}
