package br.com.perfilnet.product.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import br.com.perfilnet.product.api.controller.ProductController;
import br.com.perfilnet.product.api.model.ProductModel;
import br.com.perfilnet.product.domain.model.Product;

@Configuration
public class ProductModelAssembler extends RepresentationModelAssemblerSupport<Product, ProductModel> {

	@Autowired
	private ModelMapper mapper;

	public ProductModelAssembler() {
		super(ProductController.class, ProductModel.class);
	}

	@Override
	public ProductModel toModel(Product product) {
		var productModel = createModelWithId(product.getId(), product);

		mapper.map(product, productModel);

		return productModel;
	}
}
