package br.com.perfilnet.company.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.perfilnet.common.domain.BusinessException;
import br.com.perfilnet.common.domain.EntityNotFoundException;
import br.com.perfilnet.company.domain.exception.CompanyNotFound;
import br.com.perfilnet.company.domain.model.Company;
import br.com.perfilnet.company.domain.model.Company_;
import br.com.perfilnet.company.domain.repository.CompanyRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompanyService {

	private final CompanyRepository companyRepository;

	public Page<Company> findAll(Pageable pageable) {
		return companyRepository.findAll(pageable);
	}

	public Company findById(Long companyId) {
		return companyRepository.findById(companyId).orElseThrow(() -> new CompanyNotFound(companyId));
	}

	@Transactional
	public Company add(Company company) {
		return companyRepository.save(company);
	}

	@Transactional
	public Company update(Long companyId, Company companyUpdated) {
		try {
			var company = findById(companyId);

			BeanUtils.copyProperties(companyUpdated, company, Company_.ID, Company_.CREATED_AT, Company_.UPDATED_AT);

			return companyRepository.save(company);
		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
