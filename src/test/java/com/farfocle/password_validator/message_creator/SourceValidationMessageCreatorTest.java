package com.farfocle.password_validator.message_creator;

import com.farfocle.password_validator.InfoType;
import com.farfocle.password_validator.PasswordError;
import com.farfocle.password_validator.PasswordRuleResult;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertTrue;

public class SourceValidationMessageCreatorTest {

    @Test
    public void shouldWork(){
        List<PasswordError> errorList = Arrays.asList(PasswordError.TOO_SHORT, PasswordError.TOO_LONG, PasswordError.USERNAME);
        MessageCreatorSource source = new TestMessageSource();
        ValidationMessageCreator creator = new SourceValidationMessageCreator(source);
        boolean validationResult = creator.validate(errorList);
        assertTrue(validationResult);
        Map<InfoType, String> errorInfo = new ConcurrentHashMap<>(){{
            put(InfoType.VALID, "5");
        }};
        String message = creator.getMessage(PasswordRuleResult.createFail(PasswordError.TOO_LONG, errorInfo));
        System.out.println();

    }
}
