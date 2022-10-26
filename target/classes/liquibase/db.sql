
CREATE TABLE smartphone (
id int PRIMARY KEY AUTO_INCREMENT,
name varchar(255),
model varchar(255),
price DECIMAL(10,2)
);

CREATE TABLE orders (
client_id int,
smartphone_id int
);

CREATE TABLE client (
id int primary key AUTO_INCREMENT,
name varchar(255)
);

CREATE TABLE address (
id int primary key,
city varchar(255),
country varchar(255)
);

ALTER TABLE address ADD CONSTRAINT fk_address_client_id FOREIGN KEY (id) REFERENCES client(id);

ALTER TABLE orders ADD CONSTRAINT fk_orders_client_id FOREIGN KEY (client_id) REFERENCES client(id);
ALTER TABLE orders ADD CONSTRAINT fk_orders_smartphone_id FOREIGN KEY (smartphone_id) REFERENCES smartphone(id);