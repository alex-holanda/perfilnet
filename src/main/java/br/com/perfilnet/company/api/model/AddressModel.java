package br.com.perfilnet.company.api.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressModel extends RepresentationModel<AddressModel> {

	private String street;

	private String number;

	private String city;

	private String stateAbbr;

	private String state;

	private String district;

	private String postalCode;

	private String complement;
}
