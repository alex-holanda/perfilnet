package br.com.perfilnet.product.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import br.com.perfilnet.company.api.model.CompanySummaryModel;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "products")
@Getter
@Setter
public class ProductModel extends RepresentationModel<ProductModel> {

	private Long id;

	private CompanySummaryModel company;

	private String type;

	private BigDecimal price;
}
