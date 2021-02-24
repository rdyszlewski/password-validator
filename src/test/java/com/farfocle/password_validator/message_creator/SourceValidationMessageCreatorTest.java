package com.farfocle.password_validator.message_creator;

import com.farfocle.password_validator.InfoType;
import com.farfocle.password_validator.PasswordError;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SourceValidationMessageCreatorTest {

    private ValidationMessageCreator creator;

    @Before
    public void initCreator() {
        MessageCreatorSource source = new TestMessageSource();
        creator = new SourceValidationMessageCreator(source);
    }

    @Test
    public void shouldSuccessfullyValidMessageCreator() throws MessageCreatorValidationException {
        List<InfoType> minLengthInfo = Collections.singletonList(InfoType.VALID);
        List<InfoType> maxLengthInfo = Collections.singletonList(InfoType.VALID);
        List<InfoType> usernameLengthInfo = Collections.singletonList(InfoType.VALID);
        List<MessageValidationRule> rules = Arrays.asList(
                new MessageValidationRule(PasswordError.TOO_SHORT, minLengthInfo),
                new MessageValidationRule(PasswordError.TOO_LONG, maxLengthInfo),
                new MessageValidationRule(PasswordError.USERNAME, usernameLengthInfo)
        );

        boolean validationResult = creator.validate(rules);
        assertTrue(validationResult);
    }

    @Test
    public void shouldFailValidationMessageCreator() {
        List<InfoType> minLengthInfo = Collections.singletonList(InfoType.VALID);
        List<InfoType> maxLengthInfo = new ArrayList<>();
        List<InfoType> usernameLengthInfo = Collections.singletonList(InfoType.VALID);
        List<MessageValidationRule> rules = Arrays.asList(
                new MessageValidationRule(PasswordError.TOO_SHORT, minLengthInfo),
                new MessageValidationRule(PasswordError.TOO_LONG, maxLengthInfo),
                new MessageValidationRule(PasswordError.USERNAME, usernameLengthInfo)
        );
        try {
            creator.validate(rules);
        } catch (MessageCreatorValidationException e) {
            assertEquals(PasswordError.TOO_LONG, e.getError());
            assertEquals(InfoType.VALID, e.getInfoType());
        }
    }
}
