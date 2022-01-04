package br.com.perfilnet.company.api.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.perfilnet.common.api.utils.ApiUtils;
import br.com.perfilnet.company.api.assembler.CompanyModelAssembler;
import br.com.perfilnet.company.api.assembler.CompanySummaryModelAssembler;
import br.com.perfilnet.company.api.disassembler.CompanyInputDisassembler;
import br.com.perfilnet.company.api.model.CompanyModel;
import br.com.perfilnet.company.api.model.CompanySummaryModel;
import br.com.perfilnet.company.api.model.input.CompanyInput;
import br.com.perfilnet.company.domain.model.Company;
import br.com.perfilnet.company.domain.service.CompanyService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/companies", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyController {

	private final CompanyService companyService;

	private final PagedResourcesAssembler<Company> pagedResourceAssembler;

	private final CompanySummaryModelAssembler companySummaryModelAssembler;

	private final CompanyModelAssembler companyModelAssembler;

	private final CompanyInputDisassembler companyInputDisassembler;

	@GetMapping
	public ResponseEntity<PagedModel<CompanySummaryModel>> getAll(Pageable pageable) {
		var pagedCompany = companyService.findAll(pageable);
		var companiesModel = pagedResourceAssembler.toModel(pagedCompany, companySummaryModelAssembler);
		return ResponseEntity.ok(companiesModel);
	}

	@GetMapping("/{companyId}")
	public ResponseEntity<CompanyModel> getById(@PathVariable Long companyId) {
		var company = companyService.findById(companyId);
		var companyModel = companyModelAssembler.toModel(company);

		return ResponseEntity.ok(companyModel);
	}

	@PostMapping
	public ResponseEntity<CompanyModel> add(@Valid @RequestBody CompanyInput companyInput) {
		var company = companyInputDisassembler.toDomainObject(companyInput);
		company = companyService.add(company);

		var companyModel = companyModelAssembler.toModel(company);

		var uri = ApiUtils.uri(companyModel.getId());

		return ResponseEntity.created(uri).body(companyModel);
	}

	@PutMapping("/{companyId}")
	public ResponseEntity<CompanyModel> update(@PathVariable Long companyId,
			@Valid @RequestBody CompanyInput companyInput) {

		var company = companyInputDisassembler.toDomainObject(companyInput);
		company = companyService.update(companyId, company);

		var companyModel = companyModelAssembler.toModel(company);

		return ResponseEntity.ok(companyModel);
	}
}
