package com.example.Blogging_S6_L2.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(int id){
        super("L'elemento con id " + id + " non è stato trovato!");
    }
}
