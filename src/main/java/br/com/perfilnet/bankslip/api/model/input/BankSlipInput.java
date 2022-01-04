package br.com.perfilnet.bankslip.api.model.input;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankSlipInput {

	@NotNull
	private Long productId;

	@NotNull
	private Set<MultipartFile> files = new HashSet<>();
}
