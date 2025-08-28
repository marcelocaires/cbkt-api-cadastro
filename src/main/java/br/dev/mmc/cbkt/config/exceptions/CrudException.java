package br.dev.mmc.cbkt.config.exceptions;

public class CrudException extends RuntimeException {
  public CrudException(String msg) {
	  super(msg);
  }
}