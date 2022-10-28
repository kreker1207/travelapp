package com.project.trav.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityExistsException;
@ResponseStatus(value = HttpStatus.FOUND)
public class EntityAlreadyExists extends EntityExistsException {
    public EntityAlreadyExists(String message){super(message);}
}
