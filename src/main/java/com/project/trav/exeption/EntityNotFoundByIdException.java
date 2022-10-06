package com.project.trav.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundByIdException extends EntityNotFoundException {
    public EntityNotFoundByIdException(String message){super(message);}
}
