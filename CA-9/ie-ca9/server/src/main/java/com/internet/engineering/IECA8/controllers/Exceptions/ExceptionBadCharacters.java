package com.internet.engineering.IECA8.controllers.Exceptions;

public class ExceptionBadCharacters extends Exception{
    @Override
    public String getMessage() {
        return "Illegal use of tags or special characters";
    }
}
