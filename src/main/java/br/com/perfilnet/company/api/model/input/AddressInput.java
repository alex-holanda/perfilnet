package br.com.perfilnet.company.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressInput {

	@NotBlank
	private String street;

	@NotBlank
	private String number;

	@NotBlank
	private String city;

	@NotBlank
	private String stateAbbr;

	@NotBlank
	private String state;

	@NotBlank
	private String district;

	@NotBlank
	private String postalCode;

	private String complement;
}
