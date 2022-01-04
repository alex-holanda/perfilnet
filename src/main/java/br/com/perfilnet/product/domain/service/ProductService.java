package br.com.perfilnet.product.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.perfilnet.common.domain.BusinessException;
import br.com.perfilnet.common.domain.EntityNotFoundException;
import br.com.perfilnet.company.domain.service.CompanyService;
import br.com.perfilnet.product.domain.exception.ProductNotFoundException;
import br.com.perfilnet.product.domain.model.Product;
import br.com.perfilnet.product.domain.model.ProductType;
import br.com.perfilnet.product.domain.model.Product_;
import br.com.perfilnet.product.domain.repository.ProductRepository;
import br.com.perfilnet.product.infrastructure.repository.spec.ProductSpec;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	private final CompanyService companyService;

	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(ProductSpec.filter(), pageable);
	}

	public List<Product> findAll(ProductType type) {
		return productRepository.findAllByType(type);
	}

	public Product findById(Long productId) {
		return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
	}

	@Transactional
	public Product save(Product product) {
		try {
			var company = companyService.findById(product.getCompany().getId());

			product.setCompany(company);

			return productRepository.save(product);
		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Transactional
	public Product update(Long productId, Product productUpdated) {
		try {
			var product = findById(productId);
			var company = companyService.findById(productUpdated.getCompany().getId());

			BeanUtils.copyProperties(productUpdated, product, Product_.ID, Product_.COMPANY, Product_.CREATED_AT,
					Product_.UPDATED_AT);

			product.setCompany(company);

			return productRepository.save(product);
		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
