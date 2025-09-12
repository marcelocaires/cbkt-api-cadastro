package br.dev.mmc.cbkt.controller.forms;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "Informe um email.") 
	@NotEmpty(message = "Informe um email válido.") 
	@Email(message = "Informe um email válido.") 
	private String email;

	@NotNull(message = "Informe uma senha válida.") 
	@NotEmpty(message = "Informe uma senha válida.") 
	private String senha;
}
