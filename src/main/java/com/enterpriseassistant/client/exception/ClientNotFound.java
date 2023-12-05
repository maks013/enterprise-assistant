package com.enterpriseassistant.client.exception;

public class ClientNotFound extends RuntimeException{
    public ClientNotFound(){
        super("Client not found");
    }
}
