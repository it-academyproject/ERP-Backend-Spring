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

INSERT INTO employees (id, name, surname, dni, salary, phone, in_date, user_id)
	VALUES
		('3888bfca-94bd-4270-9381-1405063b9e2c', 'John', 'Doe', 'A1234567Z', '24000', '667999999', '2021-01-01', '91'),
		('4a067c12-4587-4cd8-b5c9-37e4b1e88859', 'Jane', 'Doe', 'C1234567Z', '18000', '667999997', '2021-01-01', '92'),
		('b5e611b8-5cf5-460e-bd06-fe763287363c', 'Andres', 'Guzman', 'B1236767Z', '14000', '667999998', '2021-01-01', '93'),
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
		('333130000-0000-0000-0000-000000000000', '11110000-0000-0000-0000-000000000000', '2021-02-11', '22220000-0000-0000-0000-000000000000', 'CASH', 'COMPLETED', 2000, '33330000-0000-0000-0000-000000000000', '33330000-0000-0000-0000-000000000000');
		
INSERT INTO working_hours (date, employee_id, check_in, check_out)
	VALUES
		('2021-01-01', '11110000-0000-0000-0000-000000000000', '08:00:00', '17:00:00'),
		('2021-01-02', '22220000-0000-0000-0000-000000000000', '08:00:00', '17:00:00'),
		('2021-01-01', '3888bfca-94bd-4270-9381-1405063b9e2c', '08:00:00', '17:00:00'),
		('2021-01-02', 'b5e611b8-5cf5-460e-bd06-fe763287363c', '08:00:00', '17:00:00');
		