package com.farfocle.password_validator;

import com.farfocle.password_validator.exceptions.InvalidPasswordDataException;
import com.farfocle.password_validator.message_creator.MessageCreatorSource;
import com.farfocle.password_validator.message_creator.MessageCreatorValidationException;
import com.farfocle.password_validator.message_creator.SourceValidationMessageCreator;
import com.farfocle.password_validator.message_creator.ValidationMessageCreator;
import com.farfocle.password_validator.message_creator.data.TesSuccessMessageSource;
import com.farfocle.password_validator.message_creator.data.TestFailMessageSource;
import com.farfocle.password_validator.rules.*;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class PasswordValidatorIntegrationTest {

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
    public void shouldReturnValid() throws InvalidPasswordDataException {
        List<String> passwords = Arrays.asList(
                "Kaszanka8",
                "dhjaskJ9asd"
        );
        testAllSuccess(passwords);
    }

    private void testAllSuccess(List<String> passwords) throws InvalidPasswordDataException {
        for(String password: passwords){
            testSuccess(password);
        }
    }

    private void testSuccess(String password) throws InvalidPasswordDataException {
        PasswordData passwordData = new PasswordData(password, null);
        ValidationResult result = validator.validate(passwordData);
        assertThat(result.isValid()).as(password).isTrue();
        assertThat(result.getErrors().size()).as(password).isEqualTo(0);
    }

    @Test
    public void shouldErrorDetailsWHenPasswordIsInvalid() throws InvalidPasswordDataException {
        testFail("a", PasswordError.TOO_SHORT, InfoType.VALID, MIN_LENGTH);
        testFail("fjklsjfasdkljfkl;jsdafjklsdjfklsdjlfkjsklofjljasdfkl", PasswordError.TOO_LONG, InfoType.VALID, MAX_LENGTH);
        testFail("kaszanka8", PasswordError.UPPERCASE, InfoType.VALID, UPPERCASE);
        testFail("KASZANKA8", PasswordError.LOWERCASE, InfoType.VALID, LOWERCASE);
        testFail("Kaszanka", PasswordError.DIGITS, InfoType.VALID, DIGITS);
    }

    private void testFail(String password, PasswordError error, InfoType infoType, int ruleValue) throws InvalidPasswordDataException {
        PasswordData passwordData = new PasswordData(password, null);
        ValidationResult result = validator.validate(passwordData);
        assertThat(result.isValid()).isFalse();
        assertThat(result.getErrors().size()).isEqualTo(1);
        assertThat(result.getErrors().get(0).getErrorType()).isEqualTo(error);
        assertThat(result.getErrors().get(0).getErrorInfo()).containsKey(infoType);
        assertThat(result.getErrors().get(0).getErrorInfo().get(infoType)).isEqualTo(String.valueOf(ruleValue));
    }

    @Test
    public void shouldWorkWhenCacheIsSet() throws InvalidPasswordDataException {
        validator.setCache(10);
        this.shouldReturnValid();
        this.shouldErrorDetailsWHenPasswordIsInvalid();
    }

    @Test
    public void shouldThrowInvalidPasswordDataException() {
       assertThatThrownBy(() -> validator.validate(null)).isInstanceOf(InvalidPasswordDataException.class);
       try{
           validator.validate(null);
       } catch (InvalidPasswordDataException e){
           assertThat(e.getErrorType()).isEqualTo(InvalidPasswordDataException.Type.DATA_NULL);
       }

       try{
           validator.validate(new PasswordData(null, "s"));
       } catch (InvalidPasswordDataException e){
           assertThat(e.getErrorType()).isEqualTo(InvalidPasswordDataException.Type.PASSWORD_NULL);

       }
    }

    @Test
    public void shouldReturnMessagesFromCreator() throws MessageCreatorValidationException, InvalidPasswordDataException {
        MessageCreatorSource source = new TesSuccessMessageSource();
        ValidationMessageCreator creator = new SourceValidationMessageCreator(source);
        validator.setErrorMessageCreator(creator);

        testErrorMessage("a", "Min: " + MIN_LENGTH);
        testErrorMessage("adjkasjdklajldkasjldjasldjasla", "Max: " + MAX_LENGTH);
        testErrorMessage("kaszanka8", "Uppercase: " + UPPERCASE);
        testErrorMessage("KASZANKA8", "Lowercase: " + LOWERCASE);
        testErrorMessage("Kaszanka", "Digits: " + DIGITS);

    }

    private void testErrorMessage(String password, String message) throws InvalidPasswordDataException {
        PasswordData passwordData = new PasswordData(password);
        ValidationResult result = validator.validate(passwordData);
        assertThat(result.getErrors().get(0).getMessage()).isEqualTo(message);
    }

    @Test(expected = MessageCreatorValidationException.class)
    public void shouldThrowMessageCreatorValidationException() throws MessageCreatorValidationException {
        MessageCreatorSource source = new TestFailMessageSource();
        ValidationMessageCreator creator = new SourceValidationMessageCreator(source);
        validator.setErrorMessageCreator(creator);
    }

}
