package com.example.demo.ExceptionHandlers;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


public class ValidationResult {

    public boolean IsValid ;
    private List<String> errorMessages;

    public boolean isValid() {
        return IsValid;
    }

    public void setValid(boolean valid) {
        IsValid = valid;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public ValidationResult(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public ValidationResult() {
    }
}
