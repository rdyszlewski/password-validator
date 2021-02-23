package com.farfocle.password_validator.exceptions;

public final class InvalidPasswordDataException extends Exception{
    public enum Type{
        DATA_NULL,
        PASSWORD_NULL,
        USERNAME_NULL
    }

    private final Type errorType;

    public InvalidPasswordDataException(Type type){
        this.errorType = type;
    }

    public Type getErrorType(){
        return this.errorType;
    }

    @Override
    public String getMessage(){
        switch (this.errorType){
            case DATA_NULL:
                return "Password data is null";
            case PASSWORD_NULL:
                return "Password is null";
            case USERNAME_NULL:
                return "Username is null";
            default:
                throw new IllegalArgumentException();
        }
    }
}
