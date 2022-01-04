package br.com.perfilnet.company.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.perfilnet.company.api.controller.CompanyController;
import br.com.perfilnet.company.api.model.CompanyModel;
import br.com.perfilnet.company.domain.model.Company;

@Component
public class CompanyModelAssembler extends RepresentationModelAssemblerSupport<Company, CompanyModel> {

	@Autowired
	private ModelMapper mapper;

	public CompanyModelAssembler() {
		super(CompanyController.class, CompanyModel.class);
	}

	@Override
	public CompanyModel toModel(Company company) {
		var companyModel = createModelWithId(company.getId(), company);

		mapper.map(company, companyModel);

		return companyModel;
	}
}
