package br.com.perfilnet.company.api.model;

import java.time.OffsetDateTime;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyModel extends RepresentationModel<CompanyModel> {

	private Long id;

	private String name;

	private String cnpj;

	private String phone;

	private String email;

	private AddressModel address;

	private OffsetDateTime createdAt;

	private OffsetDateTime updatedAt;
}
