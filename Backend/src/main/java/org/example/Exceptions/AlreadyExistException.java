package org.example.Exceptions;

public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException() {
        super("This booking already exists. Please choose other places");
    }
}
