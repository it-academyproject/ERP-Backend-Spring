INSERT INTO users (id, active, last_session, password, user_type, username) VALUES
('900000000', '1', '2021-06-11 09:27:07', '$2a$10$o8tcNE1BVYvrXLSyMCfWEuFgcnu6NU0UXKb1D5.qBgLl8eeXzH/am', 'ADMIN', 'testAdmin@erp.com'),
('900000001', '1', '2021-06-11 09:27:07', '$2a$10$o8tcNE1BVYvrXLSyMCfWEuFgcnu6NU0UXKb1D5.qBgLl8eeXzH/am', 'EMPLOYEE', 'testEmployee01@erp.com'),
('900000002', '1', '2021-06-11 09:27:07', '$2a$10$o8tcNE1BVYvrXLSyMCfWEuFgcnu6NU0UXKb1D5.qBgLl8eeXzH/am', 'EMPLOYEE', 'testEmployee02@erp.com');

INSERT INTO employee (id,dni,in_date,phone,salary,user_id) VALUES 
(UNHEX(1111), 'C3333333C', '2021-2-11', '777777777', '30000', '900000001'),
(UNHEX(2222), 'C3333334C', '2021-2-11', '777777778', '20000', '900000002');

INSERT INTO addresses (id, city, country, number, street, zip_code) VALUES
(UNHEX(1111), 'Barcelona', 'Spain', '1 C', 'Calle Escorial', '08024');

INSERT INTO orders (order_id,client_id,date_created,employee_id,payment_method,status,total,billing_address_id,shipping_address_id) VALUES
(UNHEX(1111), UNHEX(1111), '2021-02-11', UNHEX(1111), 'CASH', 'COMPLETED', 3000, UNHEX(1111), UNHEX(1111)),
(UNHEX(2222), UNHEX(1111), '2021-02-11', UNHEX(1111), 'CASH','COMPLETED', 2000, UNHEX(1111), UNHEX(1111));