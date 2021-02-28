package com.farfocle.password_validator.message_creator;

import com.farfocle.password_validator.models.InfoType;
import com.farfocle.password_validator.models.PasswordError;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileValidationMessageCreatorTest {

    private FileValidationMessageCreator creator;

    @Before
    public void initCreator() throws IOException, URISyntaxException {
        URL url = getClass().getClassLoader().getResource("validation_messages.properties");
        assert url != null;
        File file = Paths.get(url.toURI()).toFile();
        creator = new FileValidationMessageCreator(file);
    }

    @Test
    public void shouldSuccessfulValidateMessageCreator()  {
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
