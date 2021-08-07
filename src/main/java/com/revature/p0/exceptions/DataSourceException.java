package com.revature.p0.exceptions;

public class DataSourceException extends RuntimeException{
    public DataSourceException(String message, Throwable cause){
            super(message, cause);
    }
}
