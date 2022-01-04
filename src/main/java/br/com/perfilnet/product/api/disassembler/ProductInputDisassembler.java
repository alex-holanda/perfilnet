package br.com.perfilnet.product.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.perfilnet.product.api.model.input.ProductInput;
import br.com.perfilnet.product.domain.model.Product;

@Component
public class ProductInputDisassembler {

	@Autowired
	private ModelMapper mapper;

	public Product toDomainObject(ProductInput productInput) {
		return mapper.map(productInput, Product.class);
	}
}
