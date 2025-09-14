package br.dev.mmc.cbkt.controller.forms;

import java.io.Serializable;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtletaValidarForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "Informe um email.") 
	@NotEmpty(message = "Informe um email válido.") 
	@Email(message = "Informe um email válido.") 
	private String email;

	@NotNull(message = "Informe a data de nascimento.") 
	@NotEmpty(message = "Informe a data de nascimento.") 
	private String dtNascimento;

	@NotNull(message = "Informe o CPF.") 
	@NotEmpty(message = "Informe o CPF.") 
	@CPF(message = "CPF inválido.") 
	private String cpf;
}
