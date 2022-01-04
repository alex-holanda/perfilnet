package br.com.perfilnet.product.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import br.com.perfilnet.company.domain.model.Company;
import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@Enumerated(EnumType.STRING)
	private ProductType type;

	private BigDecimal price;

	@CreatedDate
	private OffsetDateTime createdAt;

	@LastModifiedBy
	private OffsetDateTime updatedAt;
}
