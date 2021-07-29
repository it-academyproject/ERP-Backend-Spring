INSERT INTO users (username, password, user_type) VALUES
('admin@erp.com', '$2a$10$o8tcNE1BVYvrXLSyMCfWEuFgcnu6NU0UXKb1D5.qBgLl8eeXzH/am', 'ADMIN');

INSERT INTO addresses (id, city, country, number, street, zip_code) VALUES
('11110000-0000-0000-0000-000000000000', 'City1', 'Country1', '1 A', 'C/ Aaa', '11111'),
('22220000-0000-0000-0000-000000000000', 'City2', 'Country2', '2 B', 'C/ Bbb', '22222');

INSERT INTO shops (shop_id, brand_name, company_name, nif, phone, address_id, web_address) VALUES
('11110000-0000-0000-0000-000000000000', 'Brand1', 'Company1', '11111111A', 666666666, '11110000-0000-0000-0000-000000000000', 'www.shop1.com'),
('22220000-0000-0000-0000-000000000000', 'Brand2', 'Company2', '22222222B', 777777777, '22220000-0000-0000-0000-000000000000', 'www.shop2.com');