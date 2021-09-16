INSERT INTO users (id, username, password, last_session, registration_date, user_type)
	VALUES
		(1, 'admin@erp.com', '$2a$10$NzRLDUAxTvqEk9qRjC42Ou8xLQbjobSgOWTZwnGqo8SfYW.RK5cPS', NULL, NULL, 'ADMIN'),
		(2, 'employee@erp.com', '$2a$10$QZgGk8xKuJp5JRp4udqDDuUx0uafvisv3H2KXfvPUu0KqR08RVN9i', NULL, NULL, 'EMPLOYEE'),
		(3, 'client@erp.com', '$2a$10$b/dVgnqDJDO0LKpDN/hta.6ZMW4o94tY7ceOijux7/scPhIZQLL9C', NULL, NULL, 'CLIENT'),
		(4, 'userclient0@example.com', '$2a$10$oXYOAj2TPpOocqKZFtNs0.C3Q3m56dN3it4FXi8WILLFta4R7Rpoe', NULL, NULL, 'CLIENT'),
		(5, 'userclient1@example.com', '$2a$10$NmwzxSxoGqtwoyrjfUxAJuXmFk3BecMX2D6lmC21L0wzvBPVdF0VK', NULL, NULL, 'CLIENT'),
		(6, 'userclient2@example.com', '$2a$10$c4nhAlSAFfRxVfznoK64Huto3MJ/vZiMotqdr8bu5W0uLBjeJNHuO', NULL, NULL, 'CLIENT'),
		(7, 'userclient3@example.com', '$2a$10$gfHdkq7eEVpPMDF4DNe.NOSflt0FhyHkrPx7pAQw8WTNx1lpwoJVu', NULL, NULL, 'CLIENT'),
		(8, 'userclient4@example.com', '$2a$10$dVsGQ2F6OoFLAmuLQQ90F.HdpHh4jvY/7UUKRRDGtfmCvbUtccgR2', NULL, NULL, 'CLIENT'),
		(9, 'employee1@company.com', '$2a$10$20IQXwDc2uwFLxd3yjhTt.kXpOsaHuJdz/PgX0Aa5mKmD3f.eH4sq', NULL, NULL, 'EMPLOYEE'),
		(10, 'useremployeetwo@example.com', '$2a$10$jcFLZgHqGQBVCBWGspQ0yOi3u.VXnAIOV.fuwddGG0igrAEmZuhXm', NULL, NULL, 'EMPLOYEE');

INSERT INTO employees (id, name, surname, dni, salary, phone, in_date, user_id)
	VALUES
		('3888bfca-94bd-4270-9381-1405063b9e2c', 'John', 'Doe', 'A1234567Z', '24000', '667999999', '2021-01-01', '9'),
		('4a067c12-4587-4cd8-b5c9-37e4b1e88859', 'Jane', 'Doe', 'C1234567Z', '18000', '667999997', '2021-01-01', '2'),
		('b5e611b8-5cf5-460e-bd06-fe763287363c', 'Andres', 'Guzman', 'B1236767Z', '14000', '667999998', '2021-01-01', '10');

INSERT INTO working_hours (date, employee_id, check_in, check_out)
	VALUES
		('2021-01-01', '3888bfca-94bd-4270-9381-1405063b9e2c', '08:00:00', '17:00:00'),
		('2021-01-02', 'b5e611b8-5cf5-460e-bd06-fe763287363c', '08:00:00', '17:00:00');

INSERT INTO addresses (id, street, number, city, country, zip_code)
	VALUES
		('3836cbf0-ec20-47ae-9f2f-4a59b89c0184', 'Calle Azores', '1 C', 'Barcelona', 'Spain', '08016'),
		('575c1d13-1480-4dcd-a0f6-05dc7910c779', 'Otra Calle Ejemplo', '1 C', 'Barcelona', 'Spain', '08016'),
		('67abca3e-26b6-4baa-8f53-ce53df5724f4', 'Otra Calle Ejemplo', '1 C', 'Barcelona', 'Spain', '08016'),
		('7d643bd3-a5a7-4c9d-9812-1a2c47e18092', 'Calle Canarias', '1 C', 'Barcelona', 'Spain', '08016'),
		('830125eb-8616-429f-99e8-831b78c061c6', 'Calle Bahamas', '1 C', 'Barcelona', 'Spain', '08016'),
		('c032d54a-758e-4673-9c48-bb13056f4eb4', 'Calle Ejemplo', '1 C', 'Barcelona', 'Spain', '08016'),
		('ce9e4681-5518-4eca-a7bb-461f50637ac4', 'Calle Azores', '1 C', 'Barcelona', 'Spain', '08016'),
		('d069fd4b-8899-45f4-a0fe-812d1a02c28f', 'Calle Botigues', '1 C', 'Barcelona', 'Spain', '08016'),
		('d37d989a-3ae5-464a-b87a-1b21dd031b9c', 'Calle Botigues', '1 C', 'Barcelona', 'Spain', '08016'),
		('e3fca320-3dd4-42ce-80f1-1fe7d9f1d6aa', 'Calle Maldivas', '1 C', 'Barcelona', 'Spain', '08016'),
		('e74bca4d-fab2-43f0-b372-92c408fc02cb', 'Calle Maldivas', '1 C', 'Barcelona', 'Spain', '08016'); 

INSERT INTO clients (id, dni, image, name_surname, address_id, user_id, shipping_address_id)
	VALUES
		('0aa0ed3d-d69c-4955-a265-be813c8bf8f3', 'B7654321C', 'url image', 'Random Name', '7d643bd3-a5a7-4c9d-9812-1a2c47e18092', 8, '7d643bd3-a5a7-4c9d-9812-1a2c47e18092'),
		('22c8598b-1dc5-4b34-99bf-3ad228609004', 'L8193737Z', 'url image', 'Random Name', '575c1d13-1480-4dcd-a0f6-05dc7910c779', 5, NULL),
		('5ff1e45a-1afd-4a5c-9413-ac87c114d64b', 'L3996994Z', 'url image', 'Random Name', 'e3fca320-3dd4-42ce-80f1-1fe7d9f1d6aa', 6, NULL),
		('76a366c5-a80f-48a1-9135-9aad6a207701', 'L4522014Z', 'url image', 'Random Name', '67abca3e-26b6-4baa-8f53-ce53df5724f4', 4, NULL),
		('93aea915-323b-43a6-a652-a406ef5fabea', 'L1234567Z', 'url image', 'Random Name', 'c032d54a-758e-4673-9c48-bb13056f4eb4', 3, NULL),
		('ff157963-7dca-4ba2-84bf-557cfe924a43', 'L1554567Z', 'url image', 'Random Name', 'e74bca4d-fab2-43f0-b372-92c408fc02cb', 7, '3836cbf0-ec20-47ae-9f2f-4a59b89c0184');

INSERT INTO shops (shop_id, brand_name, company_name, creation_date, nif, phone, web_address, address_id)
	VALUES
		('89bc2321-50f5-4592-9870-45db8300b141', 'BrandTest01', 'CompanyTest01', '2021-07-21 06:23:47', '443344F', '666777999', 'www.ShopTwo.com', 'd069fd4b-8899-45f4-a0fe-812d1a02c28f'),
		('e74bca4d-fab2-43f0-b372-92c408fc02cb', 'BrandTest01', 'CompanyTest01', '2021-07-21 06:23:47', '443344F', '666777999', 'www.ShopOne.com', 'c032d54a-758e-4673-9c48-bb13056f4eb4');

INSERT INTO offers (offer_id, name, discount, starts_on, ends_on, paid_quantity, free_quantity)
	VALUES
		('a1e611b8-5cf5-460e-bd06-fe763287363c', 'test discount', 0.05, '2021-08-29 00:00:00', '2021-08-09 00:00:00', NULL, NULL),
		('11110000-0000-0000-0000-000000000000', 'Carnet Jove', 0.05, NULL, NULL, NULL, NULL),
		('22220000-0000-0000-0000-000000000000', 'Carnet de socio', 0.05, NULL, NULL, NULL, NULL),
		('33330000-0000-0000-0000-000000000000', 'Summer Sale', 0.15, '2021-07-01 00:00:00', '2021-08-31 23:59:59', NULL, NULL),
		('44440000-0000-0000-0000-000000000000', 'Black Friday', 0.30, '2021-11-26 00:00:00', '2021-11-26 23:59:59', NULL, NULL),
		('55550000-0000-0000-0000-000000000000', '2x1', NULL, NULL, NULL, 1, 1),
		('66660000-0000-0000-0000-000000000000', '3x2', NULL, NULL, NULL, 2, 1);

INSERT INTO categories (category_id, name, description, offer_id)
	VALUES
		('11110000-0000-0000-0000-000000000000', 'Food', NULL, NULL),
		('22220000-0000-0000-0000-000000000000', 'Dessert', NULL, NULL),
		('33330000-0000-0000-0000-000000000000', 'Drink', NULL, NULL);

INSERT INTO products (product_id, name, stock, image, price, vat, wholesale_price, wholesale_quantity, created, modified, offer_id, category_id, shop_id)
	VALUES
		 (1, 'water', 100, 'url image', 150, 21, 100, 200, '1626853613388', '1626853613388', NULL, '33330000-0000-0000-0000-000000000000', 'e74bca4d-fab2-43f0-b372-92c408fc02cb'),
		 (2, 'pizza', 200, 'url image', 250, 21, 175, 200, '1626853613388', '1626853613388', 'a1e611b8-5cf5-460e-bd06-fe763287363c', '11110000-0000-0000-0000-000000000000', 'e74bca4d-fab2-43f0-b372-92c408fc02cb'),
		 (3, 'pasta', 50, 'url image', 350, 21, 250, 100, '1626853613388', '1626853613388', NULL, '11110000-0000-0000-0000-000000000000', NULL),
		 (4, 'flan', 0, 'image nº4', 0, 21, 0, 0, '1626853613388', '1626853613388', NULL, '22220000-0000-0000-0000-000000000000', '89bc2321-50f5-4592-9870-45db8300b141'),
		 (5, 'ice cream', 0, 'image nº5', 0, 21, 0, 0, '1626853613388', '1626853613388', NULL, '22220000-0000-0000-0000-000000000000', '89bc2321-50f5-4592-9870-45db8300b141'),
		 (6, 'yogurt', 0, 'image nº6', 0, 21, 0, 0, '1626853613388', '1626853613388', NULL, '22220000-0000-0000-0000-000000000000', '89bc2321-50f5-4592-9870-45db8300b141'),
		 (7, 'bread', 0, 'image nº7', 0, 21, 0, 0, '1626853613388', '1626853613388', NULL, '11110000-0000-0000-0000-000000000000', '89bc2321-50f5-4592-9870-45db8300b141'),
		 (8, 'rice', 0, 'image nº8', 0, 21, 0, 0, '1626853613388', '1626853613388', NULL, '11110000-0000-0000-0000-000000000000', '89bc2321-50f5-4592-9870-45db8300b141'),
		 (9, 'soup', 0, 'image nº9', 0, 21, 0, 0, '1626853613388', '1626853613388', NULL, '11110000-0000-0000-0000-000000000000', '89bc2321-50f5-4592-9870-45db8300b141'),
		 (10, 'paella', 0, 'image nº10', 0, 21, 0, 0, '1626853613388', '1626853613388', NULL, '11110000-0000-0000-0000-000000000000', '89bc2321-50f5-4592-9870-45db8300b141'),
		 (11, 'soda', 0, 'image nº11', 0, 21, 0, 0, '1626853613388', '1626853613388', NULL, '33330000-0000-0000-0000-000000000000', '89bc2321-50f5-4592-9870-45db8300b141'),
		 (12, 'cake', 0, 'image nº12', 0, 21, 0, 0, '1626853613388', '1626853613388', NULL, '22220000-0000-0000-0000-000000000000', '89bc2321-50f5-4592-9870-45db8300b141');

INSERT INTO orders (order_id, client_id, date_created, employee_id, payment_method, status, total, billing_address_id, shipping_address_id)
	VALUES
		('0f010722-7425-4d6d-8a48-ac6ec38bdcf6', '0aa0ed3d-d69c-4955-a265-be813c8bf8f3', '2021-07-21 06:23:47', 'b5e611b8-5cf5-460e-bd06-fe763287363c', 'PAYPAL', 'PENDING_DELIVERY', '1200', '7d643bd3-a5a7-4c9d-9812-1a2c47e18092', '7d643bd3-a5a7-4c9d-9812-1a2c47e18092'),
		('173e9e5a-f489-49f4-a502-38b29f6e014d', '0aa0ed3d-d69c-4955-a265-be813c8bf8f3', '2021-07-21 06:23:47', 'b5e611b8-5cf5-460e-bd06-fe763287363c', 'PAYPAL', 'COMPLETED','1200', 'e3fca320-3dd4-42ce-80f1-1fe7d9f1d6aa', 'ce9e4681-5518-4eca-a7bb-461f50637ac4'),
		('28fcaf2e-91c1-4e38-98e7-69398feca6b2', 'ff157963-7dca-4ba2-84bf-557cfe924a43', '2021-07-21 06:23:47', 'b5e611b8-5cf5-460e-bd06-fe763287363c', 'CREDIT_CARD', 'COMPLETED', '550', 'e3fca320-3dd4-42ce-80f1-1fe7d9f1d6aa', '3836cbf0-ec20-47ae-9f2f-4a59b89c0184'),
		('9ebcaeaf-4b2f-48de-8a4e-dafa630965a6', 'ff157963-7dca-4ba2-84bf-557cfe924a43', '2021-07-21 06:23:47', 'b5e611b8-5cf5-460e-bd06-fe763287363c', 'CASH', 'IN_DELIVERY', '1200', 'e3fca320-3dd4-42ce-80f1-1fe7d9f1d6aa', '3836cbf0-ec20-47ae-9f2f-4a59b89c0184');

INSERT INTO order_details (id, quantity, subtotal, order_id, product_id)
	VALUES
		('5400c1b6-2766-4d44-a5ed-bad9e5f635db', '2', '700', '9ebcaeaf-4b2f-48de-8a4e-dafa630965a6', 3),
		('66f2e44c-c7bc-43b9-9ce6-043a01888b8b', '1', '150', '28fcaf2e-91c1-4e38-98e7-69398feca6b2', 1),
		('87add73c-bf07-4cba-a63f-bd1f2a31b805', '2', '500', '9ebcaeaf-4b2f-48de-8a4e-dafa630965a6', 2),
		('b98b8986-b545-426c-bb63-353bd0887715', '1', '150', '173e9e5a-f489-49f4-a502-38b29f6e014d', 1),
		('bd101b7a-1581-4367-8d49-8d2683854476', '2', '300', '0f010722-7425-4d6d-8a48-ac6ec38bdcf6', 1),
		('c939dd14-b540-49fb-82e3-c236c97911cc', '3', '1050', '28fcaf2e-91c1-4e38-98e7-69398feca6b2', 3),
		('d978a6e2-948b-451b-b6a1-91c377e70def', '1', '250', '0f010722-7425-4d6d-8a48-ac6ec38bdcf6', 2),
		('fc3aa971-74dc-4424-ac33-c3155eb254ce', '3', '1050', '173e9e5a-f489-49f4-a502-38b29f6e014d', 3);
