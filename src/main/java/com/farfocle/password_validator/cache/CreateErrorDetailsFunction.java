package com.farfocle.password_validator.cache;

import com.farfocle.password_validator.models.ErrorDetails;
import com.farfocle.password_validator.models.PasswordRuleResult;

import java.util.function.Function;

@FunctionalInterface
public interface CreateErrorDetailsFunction extends Function<PasswordRuleResult, ErrorDetails> {
}
