package br.com.perfilnet.company.domain.repository;

import br.com.perfilnet.common.jpa.CustomJpaRepository;
import br.com.perfilnet.company.domain.model.Company;

public interface CompanyRepository extends CustomJpaRepository<Company, Long> {

}
