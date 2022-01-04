package br.com.perfilnet.product.infrastructure.repository.spec;

import org.springframework.data.jpa.domain.Specification;

import br.com.perfilnet.product.domain.model.Product;
import br.com.perfilnet.product.domain.model.Product_;

public class ProductSpec {

	public static Specification<Product> filter() {
		return (root, query, builder) -> {
			if (Product.class.equals(query.getResultType())) {
				root.fetch(Product_.COMPANY);
			}

			return builder.and();
		};
	}
}
