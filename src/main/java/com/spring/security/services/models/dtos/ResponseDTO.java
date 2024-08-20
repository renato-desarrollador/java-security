package com.spring.security.services.models.dtos;

public class ResponseDTO {
    //Variables
    private int numOfErrors;
    private String message;

    //Generamos los getters y setters
    public int getNumOfErrors() {
        return numOfErrors;
    }

    public void setNumOfErrors(int numOfErrors) {
        this.numOfErrors = numOfErrors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
