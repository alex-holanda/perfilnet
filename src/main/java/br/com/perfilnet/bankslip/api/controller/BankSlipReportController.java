package br.com.perfilnet.bankslip.api.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.perfilnet.bankslip.domain.filter.BankSlipFilter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/bankslips/reports", produces = MediaType.APPLICATION_JSON_VALUE)
public class BankSlipReportController {

	@GetMapping("/report")
	public ResponseEntity<Void> report(BankSlipFilter filter) {

		System.out.println(">>> " + filter.getCompanyId());
		System.out.println(">>> " + filter.getCreatedAtBegin());
		System.out.println(">>> " + filter.getCreatedAtEnd());

		return ResponseEntity.noContent().build();
	}

}
