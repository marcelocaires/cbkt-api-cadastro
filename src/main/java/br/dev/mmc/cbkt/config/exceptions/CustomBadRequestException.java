package br.dev.mmc.cbkt.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomBadRequestException extends ResponseStatusException {
  public CustomBadRequestException(String msg) {
	  super(HttpStatus.BAD_REQUEST,msg);
  }
}