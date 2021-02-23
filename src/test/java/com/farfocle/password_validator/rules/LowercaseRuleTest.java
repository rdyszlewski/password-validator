//package com.farfocle.password_validator.rules;
//
//import com.farfocle.password_validator.PasswordData;
//import com.farfocle.password_validator.PasswordError;
//import org.junit.Test;
//
//import static com.farfocle.password_validator.test_utils.TestUtils.*;
//import static org.junit.Assert.assertEquals;
//
//public class LowercaseRuleTest {
//
//    // TODO: dodać testy z przyjmowanymi znakami
//
//    @Test
//    public void shouldReturnFalseWhenNotEnoughLowercase() {
//        Rule rule = new LowercaseRule(2);
//        testPasswordFail("AAAA", rule);
//        testPasswordFail("AAaAA", rule);
//        testPasswordFail("aAAAA", rule);
//        testPasswordFail("AAAAa", rule);
//    }
//
//    @Test
//    public void shouldReturnTrueWhenEnoughLowercase() {
//        Rule rule = new LowercaseRule(2);
//        testPasswordSuccess("AaaAaa", rule);
//        testPasswordSuccess("aaaaaa", rule);
//        testPasswordSuccess("AaAaA", rule);
//    }
//
//    @Test
//    public void shouldThrowNullPasswordException() {
//        Rule rule = new LowercaseRule(2);
//        testException(null, NullPointerException.class, rule);
//
//        PasswordData nullPassword = new PasswordData(null);
//        testException(nullPassword, NullPointerException.class, rule);
//    }
//
//    @Test
//    public void shouldReturnCorrectErrorDetails() {
//        Rule rule = new LowercaseRule(2);
//        assertEquals(PasswordError.LOWERCASE, rule.getErrorDetails().getErrorType());
//        assertEquals("2", rule.getErrorDetails().getValidValue());
//    }
//}
