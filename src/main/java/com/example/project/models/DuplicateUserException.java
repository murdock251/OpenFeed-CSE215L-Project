package com.example.project.models;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String message)
    {
        super(message);
    }
}