package com.revature.p0.exceptions;

public class DocumentNotFoundException extends RuntimeException{
    public DocumentNotFoundException(){
        super("Document not found.");
    }
}
