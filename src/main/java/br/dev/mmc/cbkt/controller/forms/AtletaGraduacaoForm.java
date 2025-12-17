package br.dev.mmc.cbkt.controller.forms;

import java.io.Serializable;
import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtletaGraduacaoForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "Informe o ID do atleta.") 
	private Long atletaId;

	@NotNull(message = "Informe o ID da graduação.") 
	private Long graduacaoId;

	@NotNull(message = "Informe a data do exame.") 
	private Date dtExame;

	@NotNull(message = "Informe a nota do exame.")
	private Double notaExame;
}
