package br.com.perfilnet.product.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import br.com.perfilnet.common.jpa.CustomJpaRepository;
import br.com.perfilnet.product.domain.model.Product;
import br.com.perfilnet.product.domain.model.ProductType;

public interface ProductRepository extends CustomJpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

	@Query("from Product p join fetch p.company where p.type = :type")
	List<Product> findAllByType(ProductType type);
	
}
