package br.com.perfilnet.bankslip.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.perfilnet.bankslip.api.controller.BankSlipController;
import br.com.perfilnet.bankslip.api.model.BankSlipSummaryModel;
import br.com.perfilnet.bankslip.domain.model.BankSlip;

@Component
public class BankSlipSummaryModelAssembler extends RepresentationModelAssemblerSupport<BankSlip, BankSlipSummaryModel> {

	@Autowired
	private ModelMapper mapper;

	public BankSlipSummaryModelAssembler() {
		super(BankSlipController.class, BankSlipSummaryModel.class);
	}

	@Override
	public BankSlipSummaryModel toModel(BankSlip bankSlip) {
		var bankSlipSummaryModel = createModelWithId(bankSlip.getId(), bankSlip);

		mapper.map(bankSlip, bankSlipSummaryModel);

		return bankSlipSummaryModel;
	}


}
