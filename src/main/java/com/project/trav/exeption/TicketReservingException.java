package com.project.trav.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class TicketReservingException extends IllegalArgumentException{
    public TicketReservingException(String message){super(message);}
}
