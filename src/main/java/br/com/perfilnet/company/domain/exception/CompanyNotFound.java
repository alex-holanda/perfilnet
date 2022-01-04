package br.com.perfilnet.company.domain.exception;

import br.com.perfilnet.common.domain.EntityNotFoundException;

public class CompanyNotFound extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public CompanyNotFound(Long companyId) {
		super(String.format("Não existe uma empresa com o código %s", companyId));
	}

}
