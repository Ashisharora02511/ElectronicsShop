package com.boot.electronic.store.exception;

public class BadApiResponse  extends  RuntimeException{

    public BadApiResponse(String message){
        super(message);

    }
    public BadApiResponse (){
        super("BadRequest !!");
    }
}
