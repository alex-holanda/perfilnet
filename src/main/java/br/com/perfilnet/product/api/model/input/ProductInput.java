package br.com.perfilnet.product.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import br.com.perfilnet.product.domain.model.ProductType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInput {

	@NotNull
	private ProductType type;

	@NotNull
	@PositiveOrZero
	private BigDecimal price;

	@Valid
	@NotNull
	private CompanyIdInput company;
}
