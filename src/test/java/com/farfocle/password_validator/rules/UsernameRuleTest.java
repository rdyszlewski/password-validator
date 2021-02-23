//package com.farfocle.password_validator.rules;
//
//import com.farfocle.password_validator.PasswordData;
//import com.farfocle.password_validator.PasswordError;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class UsernameRuleTest {
//
//    @Test
//    public void shouldReturnFalseWhenUsernameAndPasswordAreTheSame() {
//        Rule rule = new UsernameRule();
//        PasswordData passwordData = new PasswordData("Antek");
//        passwordData.setUsername("Antek");
//        assertFalse(rule.validate(passwordData));
//    }
//
//    @Test
//    public void shouldReturnFalseWhenUsernameAndPasswordAreDifferent() {
//        Rule rule = new UsernameRule();
//        PasswordData passwordData = new PasswordData("Antek");
//        passwordData.setUsername("Kaziu");
//        assertTrue(rule.validate(passwordData));
//    }
//
//    // TODO: zrobić test, który testuje wyrzucenie błędu, jeżeli username nie jest podany
//    @Test
//    public void shouldReturnCorrectErrorDetails() {
//        Rule rule = new UsernameRule();
//        assertEquals(PasswordError.USERNAME, rule.getErrorDetails().getErrorType());
//        assertNull(rule.getErrorDetails().getValidValue());
//    }
//}
