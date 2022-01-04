package br.com.perfilnet.company.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.perfilnet.company.api.controller.CompanyController;
import br.com.perfilnet.company.api.model.CompanySummaryModel;
import br.com.perfilnet.company.domain.model.Company;

@Component
public class CompanySummaryModelAssembler extends RepresentationModelAssemblerSupport<Company, CompanySummaryModel> {

	private final ModelMapper mapper;

	public CompanySummaryModelAssembler(ModelMapper mapper) {
		super(CompanyController.class, CompanySummaryModel.class);

		this.mapper = mapper;
	}

	@Override
	public CompanySummaryModel toModel(Company company) {
		var model = mapper.map(company, CompanySummaryModel.class);

		return model;
	}
}
