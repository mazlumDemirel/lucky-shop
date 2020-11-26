CREATE SCHEMA IF NOT EXISTS lucky_shop;
CREATE SEQUENCE IF NOT EXISTS seq_product;
CREATE SEQUENCE IF NOT EXISTS seq_user;

CREATE TABLE IF NOT EXISTS "user"
(
	id bigint not null
		constraint category_pkey
			primary key,
	created_at timestamp not null,
	deleted boolean not null,
	transaction_id varchar(255),
	updated_at timestamp,
    userType varchar(10) not null
);

CREATE TABLE IF NOT EXISTS product
(
	id bigint not null
		constraint condition_pkey
			primary key,
	created_at timestamp not null,
	deleted boolean not null,
	transaction_id varchar(255),
	updated_at timestamp,
	productType varchar(10) not null,
	price decimal not null
);
