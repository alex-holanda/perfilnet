package br.com.perfilnet.bankslip.infrastructure.repository.spec;

import org.springframework.data.jpa.domain.Specification;

import br.com.perfilnet.bankslip.domain.model.BankSlip;
import br.com.perfilnet.bankslip.domain.model.BankSlip_;

public class BankSlipSpec {

	public static Specification<BankSlip> filter() {
		return (root, query, builder) -> {
			if (BankSlip.class.equals(query.getResultType())) {
				root.fetch(BankSlip_.COMPANY);
			}

			return builder.and();
		};
	}
}
