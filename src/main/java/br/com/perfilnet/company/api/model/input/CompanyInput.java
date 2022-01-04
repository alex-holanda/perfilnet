package br.com.perfilnet.company.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyInput {

	@NotBlank
	private String name;

	@NotBlank
	private String cnpj;

	@NotBlank
	private String phone;

	@Email
	@NotBlank
	private String email;

	@Valid
	@NotNull
	private AddressInput address;

}
