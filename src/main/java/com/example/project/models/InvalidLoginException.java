package com.example.project.models;

public class InvalidLoginException extends Exception{
    public InvalidLoginException(String message){
        super(message);
    }
}