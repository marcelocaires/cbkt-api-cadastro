package br.dev.mmc.cbkt.controller.forms;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenPasswordForm implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotNull @NotEmpty private String token;
	@NotNull @NotEmpty private String novaSenha;
}
