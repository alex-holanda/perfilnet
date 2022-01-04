CREATE TABLE product (
	id				BIGINT NOT NULL AUTO_INCREMENT,
    company_id		BIGINT NOT NULL,
    type			VARCHAR(20) NOT NULL,
    price			DECIMAL(10, 2) NOT NULL,
    created_at		DATETIME NOT NULL,
    updated_at		DATETIME NOT NULL,
    
    CONSTRAINT pk_product_id PRIMARY KEY (id),
    CONSTRAINT fk_company_id FOREIGN KEY (company_id) REFERENCES company (id)
) engine=InnoDB default charset=UTF8MB4;