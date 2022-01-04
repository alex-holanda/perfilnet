package br.com.perfilnet.bankslip.domain.filter;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankSlipFilter {

	private Long companyId;

	@DateTimeFormat(iso = ISO.DATE)
	private Date createdAtBegin;

	@DateTimeFormat(iso = ISO.DATE)
	private Date createdAtEnd;
}
