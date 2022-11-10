package com.project.trav.exeption;

import javax.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PasswordMatchException extends EntityExistsException {
 public PasswordMatchException(String message) {
   super(message);
 }
}
