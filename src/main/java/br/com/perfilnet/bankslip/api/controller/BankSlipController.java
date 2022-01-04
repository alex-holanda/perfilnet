package br.com.perfilnet.bankslip.api.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.perfilnet.bankslip.api.assembler.BankSlipSummaryModelAssembler;
import br.com.perfilnet.bankslip.api.model.BankSlipSummaryModel;
import br.com.perfilnet.bankslip.domain.model.BankSlip;
import br.com.perfilnet.bankslip.domain.service.BankSlipService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/bankslips", produces = MediaType.APPLICATION_JSON_VALUE)
public class BankSlipController {

	private final BankSlipService bankSlipService;

	private final PagedResourcesAssembler<BankSlip> pagedResourcesAssembler;

	private final BankSlipSummaryModelAssembler bankSlipSummaryModelAssembler;

	@GetMapping
	public ResponseEntity<PagedModel<BankSlipSummaryModel>> getAll(Pageable pageable) {
		var pagedBankSlips = bankSlipService.getAll(pageable);
		var bankSlipsModel = pagedResourcesAssembler.toModel(pagedBankSlips, bankSlipSummaryModelAssembler);

		return ResponseEntity.ok(bankSlipsModel);
	}

}
