package br.com.perfilnet.company.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.perfilnet.company.api.model.input.CompanyInput;
import br.com.perfilnet.company.domain.model.Company;

@Component
public class CompanyInputDisassembler {

	@Autowired
	private ModelMapper mapper;

	public Company toDomainObject(CompanyInput companyInput) {
		return mapper.map(companyInput, Company.class);
	}
}
