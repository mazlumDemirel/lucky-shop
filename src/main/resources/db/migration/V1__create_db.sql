CREATE SCHEMA IF NOT EXISTS lucky_shop;
CREATE SEQUENCE IF NOT EXISTS seq_product;
CREATE SEQUENCE IF NOT EXISTS seq_user;

CREATE TABLE IF NOT EXISTS "user"
(
	id bigint not null
		constraint category_pkey
			primary key,
	created_at timestamp not null,
	transaction_id varchar(255) not null,
	user_type varchar(10) not null
);

CREATE TABLE IF NOT EXISTS product
(
	id bigint not null
		constraint condition_pkey
			primary key,
	created_at timestamp not null,
	transaction_id varchar(255) not null,
	product_type varchar(10) not null,
	price decimal not null
);
