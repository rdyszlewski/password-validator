package com.farfocle.password_validator.models;

import java.util.Collections;
import java.util.List;

public abstract class BaseValidationResult<T> {

    private final List<T> errors;

    public BaseValidationResult(List<T> errors) {
        this.errors = errors;
    }

    public boolean isValid() {
        return this.errors.isEmpty();
    }

    public List<T> getErrors() {
        return Collections.unmodifiableList(this.errors);
    }
}
