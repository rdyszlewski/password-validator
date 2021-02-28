package com.farfocle.password_validator.message_creator;

import com.farfocle.password_validator.models.PasswordData;
import com.farfocle.password_validator.PasswordValidator;
import com.farfocle.password_validator.models.ValidationResult;
import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;
import com.farfocle.password_validator.message_creator.data.TesSuccessMessageSource;
import com.farfocle.password_validator.message_creator.data.TestFailMessageSource;
import com.farfocle.password_validator.rules.*;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class SourceValidationMessageCreatorIntegrationTest {

    // TODO: poprawna walidacja
    // TODO: niepoprawna walidacja
    // TODO: zwracanie komunikatów

    private List<Rule> rules;
    private PasswordValidator validator;

    private final int MIN_LENGTH = 5;
    private final int MAX_LENGTH = 20;
    private final int UPPERCASE = 1;
    private final int LOWERCASE = 1;
    private final int DIGITS = 1;

    @Before
    public void initValidator() throws MessageCreatorValidationException {
        rules = Arrays.asList(
                new MinLengthRule.Builder(MIN_LENGTH).setInterrupting().build(),
                new MaxLengthRule.Builder(MAX_LENGTH).setInterrupting().build(),
                new UppercaseRule.Builder(UPPERCASE).build(),
                new LowercaseRule.Builder(LOWERCASE).build(),
                new DigitsRule.Builder(DIGITS).build()
        );
        validator = new PasswordValidator(rules);
    }

    @Test
    public void shouldPassValidation() throws MessageCreatorValidationException {
        MessageCreatorSource source = new TesSuccessMessageSource();
        validator.setErrorMessageCreator(new SourceValidationMessageCreator(source));
        assertTrue(true);
    }

    @Test(expected = MessageCreatorValidationException.class)
    public void shouldFailValidation() throws MessageCreatorValidationException {
        MessageCreatorSource source = new TestFailMessageSource();
        validator.setErrorMessageCreator(new SourceValidationMessageCreator(source));
        assertTrue(true);
    }

    @Test
    public void shouldReturnCorrectMessages() throws MessageCreatorValidationException, InvalidPasswordDataException {
        MessageCreatorSource source = new TesSuccessMessageSource();
        validator.setErrorMessageCreator(new SourceValidationMessageCreator(source));

        testErrorMessage("a", String.format("Min: %d", MIN_LENGTH));
        testErrorMessage("daskldjaskldjkasjjsajfljsdflsdjfljsdklfjasdklfjsdjlsadfsd", String.format("Max: %d", MAX_LENGTH));
        testErrorMessage("aaaaaaa1", String.format("Uppercase: %d", UPPERCASE));
        testErrorMessage("AAAAAAA1", String.format("Lowercase: %d", LOWERCASE));
        testErrorMessage("aaaaaaA", String.format("Digits: %d", DIGITS));
    }

    private void testErrorMessage(String password, String expectedMessage) throws InvalidPasswordDataException {
        PasswordData tooShortPassword = new PasswordData(password, null);
        ValidationResult tooShortResult = validator.validate(tooShortPassword);
        Assertions.assertThat(tooShortResult.getErrors().size()).as(tooShortPassword.getPassword()).isEqualTo(1);
        Assertions.assertThat(tooShortResult.getErrors().get(0).getMessage()).isEqualTo(expectedMessage);
    }

}
