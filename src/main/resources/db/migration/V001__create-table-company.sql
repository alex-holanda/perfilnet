CREATE TABLE company (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	cnpj VARCHAR(18) NOT NULL,
	phone VARCHAR(15),
	email VARCHAR(120) NOT NULL,
	created_at DATETIME NOT NULL,
	updated_at DATETIME NOT NULL,
	address_street VARCHAR(200) NOT NULL,
	address_number VARCHAR(10) NOT NULL,
	address_city VARCHAR(80) NOT NULL,
	address_state_abbr VARCHAR(2) NOT NULL,
	address_state VARCHAR(80) NOT NULL,
	address_district VARCHAR(120) NOT NULL,
	address_postal_code VARCHAR(9) NOT NULL,
	address_complement VARCHAR(255),
	
	
	CONSTRAINT pk_id PRIMARY KEY (id)
) engine=InnoDB default charset=UTF8MB4;