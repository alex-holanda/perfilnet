CREATE TABLE bank_slip (
	id BIGINT NOT NULL AUTO_INCREMENT,
	company_id BIGINT NOT NULL,
	amount BIGINT NOT NULL,
	price DECIMAL(10, 2) NOT NULL,
	total DECIMAL(10, 2) NOT NULL, 
	month VARCHAR(30) NOT NULL,
	year  VARCHAR(4) NOT NULL,
	file_name	VARCHAR(255) NOT NULL,
	created_at	DATETIME NOT NULL,
	updated_at	DATETIME NOT NULL,
	
	CONSTRAINT pk_bank_slip PRIMARY KEY (id),
	CONSTRAINT fk_company	FOREIGN KEY (company_id) REFERENCES company (id)
) engine=InnoDB default charset=UTF8MB4;