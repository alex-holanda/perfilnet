package br.com.perfilnet.company.domain.model;

import java.time.OffsetDateTime;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "company")
public class Company {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String cnpj;

	private String phone;

	private String email;


	@Embedded
	private Address address;

	@CreationTimestamp
	private OffsetDateTime createdAt;

	@UpdateTimestamp
	private OffsetDateTime updatedAt;

}
