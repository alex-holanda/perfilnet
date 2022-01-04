package br.com.perfilnet.bankslip.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import br.com.perfilnet.company.api.model.CompanySummaryModel;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "bankslips")
@Getter
@Setter
public class BankSlipSummaryModel extends RepresentationModel<BankSlipSummaryModel> {

	private Long id;

	private CompanySummaryModel company;

	private Integer amount;

	private BigDecimal price;

	private BigDecimal total;

	private String fileName;
}
