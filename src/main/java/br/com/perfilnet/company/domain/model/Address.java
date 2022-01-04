package br.com.perfilnet.company.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Address {

	@Column(name = "address_street")
	private String street;

	@Column(name = "address_number")
	private String number;

	@Column(name = "address_city")
	private String city;

	@Column(name = "address_state_abbr")
	private String stateAbbr;

	@Column(name = "address_state")
	private String state;

	@Column(name = "address_district")
	private String district;

	@Column(name = "address_postal_code")
	private String postalCode;

	@Column(name = "address_complement")
	private String complement;

}
