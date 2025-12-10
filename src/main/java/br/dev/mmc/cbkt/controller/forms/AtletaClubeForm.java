package br.dev.mmc.cbkt.controller.forms;

import java.io.Serializable;
import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtletaClubeForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "Informe o ID do atleta.") 
	private Long atletaId;

	@NotNull(message = "Informe o ID do clube.") 
	private Long clubeId;

	@NotNull(message = "Informe a data de admissão.") 
	private Date dtAdmissao;
}
