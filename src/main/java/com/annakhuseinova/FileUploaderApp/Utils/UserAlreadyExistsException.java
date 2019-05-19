package com.annakhuseinova.FileUploaderApp.Utils;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException(String message){

        super(message);
    }
}
