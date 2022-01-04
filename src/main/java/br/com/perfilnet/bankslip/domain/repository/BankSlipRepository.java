package br.com.perfilnet.bankslip.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.perfilnet.bankslip.domain.model.BankSlip;
import br.com.perfilnet.common.jpa.CustomJpaRepository;

public interface BankSlipRepository extends CustomJpaRepository<BankSlip, Long>, JpaSpecificationExecutor<BankSlip> {

}
