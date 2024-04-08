package com.example.demonetty.entity;
import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    String message;

    public Message(){}
    public Message(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
