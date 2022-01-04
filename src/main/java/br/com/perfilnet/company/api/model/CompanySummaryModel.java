package br.com.perfilnet.company.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Relation(collectionRelation = "companies")
public class CompanySummaryModel extends RepresentationModel<CompanySummaryModel> {

	private Long id;

	private String name;

	private String cnpj;

	private String phone;

	private String email;

}
