package com.project.trav.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TicketReservingException extends IllegalArgumentException {

  public TicketReservingException(String message) {
    super(message);
  }
}
