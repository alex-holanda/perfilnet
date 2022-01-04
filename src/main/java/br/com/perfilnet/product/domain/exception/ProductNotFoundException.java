package br.com.perfilnet.product.domain.exception;

import br.com.perfilnet.common.domain.EntityNotFoundException;

public class ProductNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(String message) {
		super(message);
	}

	public ProductNotFoundException(Long productId) {
		this(String.format("Não existe um cadastro do produto %d", productId));
	}
}
