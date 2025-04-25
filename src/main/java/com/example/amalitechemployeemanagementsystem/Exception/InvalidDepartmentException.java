package com.example.amalitechemployeemanagementsystem.Exception;

public class InvalidDepartmentException extends Exception {
    public InvalidDepartmentException(String message) {
        super(message);
    }

    public InvalidDepartmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
