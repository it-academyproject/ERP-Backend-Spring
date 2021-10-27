INSERT INTO users (id, username, password, last_session, registration_date, user_type, active)
	VALUES
		(1, 'admin@erp.com', '$2a$10$NzRLDUAxTvqEk9qRjC42Ou8xLQbjobSgOWTZwnGqo8SfYW.RK5cPS', NULL, NULL, 'ADMIN', '1'),
		(2, 'employee@erp.com', '$2a$10$QZgGk8xKuJp5JRp4udqDDuUx0uafvisv3H2KXfvPUu0KqR08RVN9i', NULL, NULL, 'EMPLOYEE', '1'),
		(3, 'client@erp.com', '$2a$10$b/dVgnqDJDO0LKpDN/hta.6ZMW4o94tY7ceOijux7/scPhIZQLL9C', NULL, NULL, 'CLIENT', '1'),
		('91', 'testAdmin@erp.com', '$2a$10$o8tcNE1BVYvrXLSyMCfWEuFgcnu6NU0UXKb1D5.qBgLl8eeXzH/am', '2021-06-11 09:27:07', NULL, 'ADMIN', '1'),
		('92', 'testEmployee@erp.com', '$2a$10$o8tcNE1BVYvrXLSyMCfWEuFgcnu6NU0UXKb1D5.qBgLl8eeXzH/am', '2021-06-11 09:27:07', NULL, 'EMPLOYEE', '1'),
		('93', 'testClient@erp.com', '$2a$10$o8tcNE1BVYvrXLSyMCfWEuFgcnu6NU0UXKb1D5.qBgLl8eeXzH/am', '2021-06-11 09:27:07', NULL, 'CLIENT', '1'),
		('94', 'testEmployee01@erp.com', '$2a$10$o8tcNE1BVYvrXLSyMCfWEuFgcnu6NU0UXKb1D5.qBgLl8eeXzH/am', '2021-06-11 09:27:07', NULL, 'EMPLOYEE', '1'),
		('95', 'testEmployee02@erp.com', '$2a$10$o8tcNE1BVYvrXLSyMCfWEuFgcnu6NU0UXKb1D5.qBgLl8eeXzH/am', '2021-06-11 09:27:07', NULL, 'EMPLOYEE', '1');
		
INSERT INTO offers (offer_id, name, discount, starts_on, ends_on, paid_quantity, free_quantity)
	VALUES
		('a1e611b8-5cf5-460e-bd06-fe763287363c', 'test discount', 0.05, '2021-08-29 00:00:00', '2021-08-09 00:00:00', -1, -1),
		('11110000-0000-0000-0000-000000000000', 'test Carnet Jove', 0.05, NULL, NULL, -1, -1),
		('22220000-0000-0000-0000-000000000000', 'test Carnet de socio', 0.05, NULL, NULL, -1, -1),
		('33330000-0000-0000-0000-000000000000', 'test Summer Sale', 0.15, '2021-07-01 00:00:00', '2021-08-31 23:59:59', -1, -1),
		('44440000-0000-0000-0000-000000000000', 'test Black Friday', 0.30, '2021-11-26 00:00:00', '2021-11-26 23:59:59', -1, -1),
		('55550000-0000-0000-0000-000000000000', '2x1', -1, NULL, NULL, 1, 1),
		('66660000-0000-0000-0000-000000000000', '3x2', -1, NULL, NULL, 2, 1);

INSERT INTO employees (id, name, surname, dni, salary, phone, in_date, user_id)
	VALUES
		('11110000-0000-0000-0000-000000000000', 'juan', 'perez', 'C3333333C', '30000', '777777777', '2021-2-11', '94'),
		('22220000-0000-0000-0000-000000000000', 'juan', 'perez', 'C3333334C', '20000', '777777778', '2021-2-11', '95');

INSERT INTO addresses (id, street, number, city, country, zip_code)
	VALUES
		('11110000-0000-0000-0000-000000000000', 'C/ Aaa', '1 A', 'City1', 'Country1', '11111'),
		('22220000-0000-0000-0000-000000000000', 'C/ Bbb', '2 B', 'City2', 'Country2', '22222'),
		('33330000-0000-0000-0000-000000000000', 'Calle Escorial', '1 C', 'Barcelona', 'Spain', '08024'),
		('44440000-0000-0000-0000-000000000000', 'Calle Escorial', '1 C', 'Barcelona', 'Spain', '08024');

INSERT INTO clients (id, dni, image, name_surname, address_id, user_id, shipping_address_id)
	VALUES
		('11110000-0000-0000-0000-000000000000', 'B7654321C', 'url image', 'Random Name', '44440000-0000-0000-0000-000000000000', 3, NULL);

INSERT INTO shops (shop_id, brand_name, company_name, creation_date, nif, phone, web_address, address_id)
	VALUES
		('11110000-0000-0000-0000-000000000000', 'Brand1', 'Company1', '2021-01-01 01:01:01', '11111111A', 666666666, 'www.shop1.com', '11110000-0000-0000-0000-000000000000'),
		('22220000-0000-0000-0000-000000000000', 'Brand2', 'Company2', '2021-02-02 02:02:02', '22222222B', 777777777, 'www.shop2.com', '22220000-0000-0000-0000-000000000000');

INSERT INTO orders (order_id, client_id, date_created, employee_id, payment_method, status, total, billing_address_id, shipping_address_id)
	VALUES
		('11110000-0000-0000-0000-000000000000', '11110000-0000-0000-0000-000000000000', '2021-02-11', '11110000-0000-0000-0000-000000000000', 'CASH', 'COMPLETED', 3000, '33330000-0000-0000-0000-000000000000', '33330000-0000-0000-0000-000000000000'),
		('22220000-0000-0000-0000-000000000000', '11110000-0000-0000-0000-000000000000', '2021-02-11', '11110000-0000-0000-0000-000000000000', 'CASH', 'COMPLETED', 2000, '33330000-0000-0000-0000-000000000000', '33330000-0000-0000-0000-000000000000'),
		('33330000-0000-0000-0000-000000000000', '11110000-0000-0000-0000-000000000000', '2021-02-11', '22220000-0000-0000-0000-000000000000', 'CASH', 'COMPLETED', 2000, '33330000-0000-0000-0000-000000000000', '33330000-0000-0000-0000-000000000000');