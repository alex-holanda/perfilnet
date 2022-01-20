package br.com.perfilnet.bankslip.infrastructure.repository.spec;

import java.time.ZoneOffset;
import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.perfilnet.bankslip.domain.filter.BankSlipFilter;
import br.com.perfilnet.bankslip.domain.model.BankSlip;
import br.com.perfilnet.bankslip.domain.model.BankSlip_;

public class BankSlipSpec {

	public static Specification<BankSlip> filter(BankSlipFilter filter) {
		return (root, query, builder) -> {
			if (BankSlip.class.equals(query.getResultType())) {
				root.fetch(BankSlip_.COMPANY);
			}

			var predicates = new ArrayList<Predicate>();

			if (filter.getCompanyId() != null) {
				predicates.add(builder.equal(root.get(BankSlip_.COMPANY), filter.getCompanyId()));
			}

			if (filter.getCreatedAtBegin() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get(BankSlip_.CREATED_AT),
						filter.getCreatedAtBegin().toInstant().atOffset(ZoneOffset.UTC)));
			}
			
			if (filter.getCreatedAtEnd() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get(BankSlip_.CREATED_AT),
						filter.getCreatedAtEnd().toInstant().atOffset(ZoneOffset.UTC)));
			}

			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
	}
}
