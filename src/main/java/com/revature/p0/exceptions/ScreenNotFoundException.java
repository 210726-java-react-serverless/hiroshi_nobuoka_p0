package com.revature.p0.exceptions;

public class ScreenNotFoundException extends RuntimeException{
    public ScreenNotFoundException(){
        super("Screen not found with provided route!");
    }
}
