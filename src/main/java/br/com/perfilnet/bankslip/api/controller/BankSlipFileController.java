package br.com.perfilnet.bankslip.api.controller;

import java.io.ByteArrayInputStream;

import javax.validation.Valid;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.perfilnet.bankslip.api.model.input.BankSlipInput;
import br.com.perfilnet.bankslip.api.model.input.ResourceInput;
import br.com.perfilnet.bankslip.domain.service.BankSlipJoinService;
import br.com.perfilnet.bankslip.domain.service.BankSlipService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/bankslips/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public class BankSlipFileController {

	private final BankSlipService bankSlipService;
	
	private final BankSlipJoinService bankSlipJoinService;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> add(@Valid BankSlipInput bankSlipInput) {
		bankSlipService.add(bankSlipInput.getProductId(), bankSlipInput.getFiles());

		return ResponseEntity.noContent().build();
	}

	@PostMapping(path = "/join", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<Resource> join(ResourceInput resourceInput) {
		var joinedDocument = bankSlipJoinService.join(resourceInput.getFiles());

		var resource = new InputStreamResource(new ByteArrayInputStream(joinedDocument));

		return ResponseEntity.ok().contentLength(joinedDocument.length).contentType(MediaType.APPLICATION_PDF)
				.body(resource);
	}
}
