package br.com.perfilnet.bankslip.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.perfilnet.company.domain.model.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bank_slip")
public class BankSlip {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	private Integer amount;

	private BigDecimal price;

	private BigDecimal total;

	private String month;

	private String year;

	private String fileName;

	@CreationTimestamp
	private OffsetDateTime createdAt;

	@UpdateTimestamp
	private OffsetDateTime updatedAt;
}
