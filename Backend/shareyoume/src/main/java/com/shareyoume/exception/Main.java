package com.shareyoume.exception;

public class Main {

    public static void main(String[] args) {
       // float a = 1/0;

        Main main = new Main();
        main.error();
    }

    public void error() throws UserNotFoundException {
        System.out.println("Error is called");
        try {
            findByUser();
        } catch (Exception e) {
            throw new UserNotFoundException("Error method thrown exception", e);
        }
    }

    public void findByUser() throws Exception {
        System.out.println("Error1 is called");
        throw new Exception("Entity not found thrown exception");
    }
}

class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
