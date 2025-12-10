package br.dev.mmc.cbkt.controller.forms;

import java.io.Serializable;
import java.util.Date;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtletaClubeTransferirForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "Informe o ID do atleta.") 
	private Long atletaId;

	@NotNull(message = "Informe o ID do clube de origem.") 
	private Long atletaClubeOrigemId;

	@NotNull(message = "Informe o ID do clube de destino.") 
	private Long clubeDestinoId;
	
	@NotNull(message = "Informe a data de transferência.") 
	private Date dtTransferencia;
}
