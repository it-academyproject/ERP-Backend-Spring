INSERT INTO users(username, password,user_type) VALUES('admin@erp.com', '$2a$10$NzRLDUAxTvqEk9qRjC42Ou8xLQbjobSgOWTZwnGqo8SfYW.RK5cPS', 'ADMIN');
INSERT INTO users(username, password,user_type) VALUES('employee@erp.com', '$2a$10$QZgGk8xKuJp5JRp4udqDDuUx0uafvisv3H2KXfvPUu0KqR08RVN9i', 'EMPLOYEE');
INSERT INTO users(username, password,user_type) VALUES('client@erp.com', '$2a$10$b/dVgnqDJDO0LKpDN/hta.6ZMW4o94tY7ceOijux7/scPhIZQLL9C', 'CLIENT');
INSERT INTO users(username, password,user_type) VALUES('userclient0@example.com', '$2a$10$oXYOAj2TPpOocqKZFtNs0.C3Q3m56dN3it4FXi8WILLFta4R7Rpoe', 'CLIENT');
INSERT INTO users(username, password,user_type) VALUES('userclient1@example.com', '$2a$10$NmwzxSxoGqtwoyrjfUxAJuXmFk3BecMX2D6lmC21L0wzvBPVdF0VK', 'CLIENT');
INSERT INTO users(username, password,user_type) VALUES('userclient2@example.com', '$2a$10$c4nhAlSAFfRxVfznoK64Huto3MJ/vZiMotqdr8bu5W0uLBjeJNHuO', 'CLIENT');
INSERT INTO users(username, password,user_type) VALUES('userclientone@example.com', '$2a$10$gfHdkq7eEVpPMDF4DNe.NOSflt0FhyHkrPx7pAQw8WTNx1lpwoJVu', 'CLIENT');
INSERT INTO users(username, password,user_type) VALUES('userclienttwo@example.com', '$2a$10$dVsGQ2F6OoFLAmuLQQ90F.HdpHh4jvY/7UUKRRDGtfmCvbUtccgR2', 'CLIENT');
INSERT INTO users(username, password,user_type) VALUES('employee1@company.com', '$2a$10$20IQXwDc2uwFLxd3yjhTt.kXpOsaHuJdz/PgX0Aa5mKmD3f.eH4sq', 'EMPLOYEE');
INSERT INTO users(username, password,user_type) VALUES('useremployeetwo@example.com', '$2a$10$jcFLZgHqGQBVCBWGspQ0yOi3u.VXnAIOV.fuwddGG0igrAEmZuhXm', 'EMPLOYEE');

INSERT INTO employee (id, name, surname,  dni, salary, phone, in_date, user_id) VALUES ('3888bfca-94bd-4270-9381-1405063b9e2c','John', 'Doe','A1234567Z', '24000', '667999999', '2021-01-01','9');
INSERT INTO employee (id, name, surname,  dni, salary, phone, in_date, user_id) VALUES ( '4a067c12-4587-4cd8-b5c9-37e4b1e88859', 'Jane', 'Doe','C1234567Z', '18000', '667999997', '2021-01-01','2');
INSERT INTO employee (id, name, surname,  dni, salary, phone, in_date, user_id) VALUES ('b5e611b8-5cf5-460e-bd06-fe763287363c', 'Andres','Guzman','B1236767Z', '14000', '667999998', '2021-01-01','10');

INSERT INTO addresses(id, street, number, city, country, zip_code)VALUES('3836cbf0-ec20-47ae-9f2f-4a59b89c0184','Calle Azores', '1 C','Barcelona','Spain', '08016'); 
INSERT INTO addresses(id, street, number, city, country, zip_code)VALUES('575c1d13-1480-4dcd-a0f6-05dc7910c779','Otra Calle Ejemplo', '1 C','Barcelona','Spain', '08016'); 
INSERT INTO addresses(id, street, number, city, country, zip_code)VALUES('67abca3e-26b6-4baa-8f53-ce53df5724f4','Otra Calle Ejemplo', '1 C','Barcelona','Spain', '08016'); 
INSERT INTO addresses(id, street, number, city, country, zip_code)VALUES('7d643bd3-a5a7-4c9d-9812-1a2c47e18092','Calle Canarias', '1 C','Barcelona','Spain', '08016'); 
INSERT INTO addresses(id, street, number, city, country, zip_code)VALUES('830125eb-8616-429f-99e8-831b78c061c6','Calle Bahamas', '1 C','Barcelona','Spain', '08016'); 
INSERT INTO addresses(id, street, number, city, country, zip_code)VALUES('c032d54a-758e-4673-9c48-bb13056f4eb4','Calle Ejemplo', '1 C','Barcelona','Spain', '08016'); 
INSERT INTO addresses(id, street, number, city, country, zip_code)VALUES('ce9e4681-5518-4eca-a7bb-461f50637ac4','Calle Azores', '1 C','Barcelona','Spain', '08016'); 
INSERT INTO addresses(id, street, number, city, country, zip_code)VALUES('d069fd4b-8899-45f4-a0fe-812d1a02c28f','Calle Botigues', '1 C','Barcelona','Spain', '08016'); 
INSERT INTO addresses(id, street, number, city, country, zip_code)VALUES('d37d989a-3ae5-464a-b87a-1b21dd031b9c','Calle Botigues', '1 C','Barcelona','Spain', '08016'); 
INSERT INTO addresses(id, street, number, city, country, zip_code)VALUES('e3fca320-3dd4-42ce-80f1-1fe7d9f1d6aa','Calle Maldivas', '1 C','Barcelona','Spain', '08016'); 
INSERT INTO addresses(id, street, number, city, country, zip_code)VALUES('e74bca4d-fab2-43f0-b372-92c408fc02cb','Calle Maldivas', '1 C','Barcelona','Spain', '08016'); 

INSERT INTO clients (id, dni, image, name_surname, address_id,user_id, shipping_address_id)VALUES('0aa0ed3d-d69c-4955-a265-be813c8bf8f3','B7654321C','url image','Random Name','7d643bd3-a5a7-4c9d-9812-1a2c47e18092','8','7d643bd3-a5a7-4c9d-9812-1a2c47e18092');
INSERT INTO clients (id, dni, image, name_surname, address_id,user_id, shipping_address_id)VALUES('22c8598b-1dc5-4b34-99bf-3ad228609004','L8193737Z','url image','Random Name','575c1d13-1480-4dcd-a0f6-05dc7910c779','5',null);
INSERT INTO clients (id, dni, image, name_surname, address_id,user_id, shipping_address_id)VALUES('5ff1e45a-1afd-4a5c-9413-ac87c114d64b','L3996994Z','url image','Random Name','e3fca320-3dd4-42ce-80f1-1fe7d9f1d6aa','6',null);
INSERT INTO clients (id, dni, image, name_surname, address_id,user_id, shipping_address_id)VALUES('76a366c5-a80f-48a1-9135-9aad6a207701','L4522014Z','url image','Random Name','67abca3e-26b6-4baa-8f53-ce53df5724f4','4',null);
INSERT INTO clients (id, dni, image, name_surname, address_id,user_id, shipping_address_id)VALUES('93aea915-323b-43a6-a652-a406ef5fabea','L1234567Z','url image','Random Name','c032d54a-758e-4673-9c48-bb13056f4eb4','3',null);
INSERT INTO clients (id, dni, image, name_surname, address_id,user_id, shipping_address_id)VALUES('ff157963-7dca-4ba2-84bf-557cfe924a43','L1554567Z','url image','Random Name','e74bca4d-fab2-43f0-b372-92c408fc02cb','7','3836cbf0-ec20-47ae-9f2f-4a59b89c0184');

INSERT INTO  working_hours(date,employee_id, check_in,check_out)VALUES('2021-01-01','3888bfca-94bd-4270-9381-1405063b9e2c', '08:00:00','17:00:00');
INSERT INTO  working_hours(date,employee_id, check_in,check_out)VALUES('2021-01-02','b5e611b8-5cf5-460e-bd06-fe763287363c', '08:00:00','17:00:00');

INSERT INTO shops(shop_id, brand_name, company_name, creation_date, nif, phone,web_address,address_id)VALUES('89bc2321-50f5-4592-9870-45db8300b141','BrandTest01','CompanyTest01','2021-07-21 06:23:47','443344F','666777999','www.ShopTwo.com','d069fd4b-8899-45f4-a0fe-812d1a02c28f');
INSERT INTO shops(shop_id, brand_name, company_name, creation_date, nif, phone,web_address,address_id)VALUES('e74bca4d-fab2-43f0-b372-92c408fc02cb','BrandTest01','CompanyTest01','2021-07-21 06:23:47','443344F','666777999','www.ShopOne.com','c032d54a-758e-4673-9c48-bb13056f4eb4');

INSERT INTO orders(order_id,client_id,date_created,employee_id,payment_method,status,total,billing_address_id,shipping_address_id)VALUES('0f010722-7425-4d6d-8a48-ac6ec38bdcf6', '0aa0ed3d-d69c-4955-a265-be813c8bf8f3', '2021-07-21 06:23:47',  'b5e611b8-5cf5-460e-bd06-fe763287363c','PAYPAL','PENDING_DELIVERY','1200','7d643bd3-a5a7-4c9d-9812-1a2c47e18092','7d643bd3-a5a7-4c9d-9812-1a2c47e18092');			  
INSERT INTO orders(order_id,client_id,date_created,employee_id,payment_method,status,total,billing_address_id,shipping_address_id)VALUES('173e9e5a-f489-49f4-a502-38b29f6e014d', '0aa0ed3d-d69c-4955-a265-be813c8bf8f3', '2021-07-21 06:23:47', 'b5e611b8-5cf5-460e-bd06-fe763287363c','PAYPAL','COMPLETED','1200','e3fca320-3dd4-42ce-80f1-1fe7d9f1d6aa','ce9e4681-5518-4eca-a7bb-461f50637ac4');			  
INSERT INTO orders(order_id,client_id,date_created,employee_id,payment_method,status,total,billing_address_id,shipping_address_id)VALUES('28fcaf2e-91c1-4e38-98e7-69398feca6b2', 'ff157963-7dca-4ba2-84bf-557cfe924a43', '2021-07-21 06:23:47', 'b5e611b8-5cf5-460e-bd06-fe763287363c','CREDIT_CARD','COMPLETED','550','e3fca320-3dd4-42ce-80f1-1fe7d9f1d6aa','3836cbf0-ec20-47ae-9f2f-4a59b89c0184');
INSERT INTO orders(order_id,client_id,date_created,employee_id,payment_method,status,total,billing_address_id,shipping_address_id)VALUES('9ebcaeaf-4b2f-48de-8a4e-dafa630965a6', 'ff157963-7dca-4ba2-84bf-557cfe924a43', '2021-07-21 06:23:47', 'b5e611b8-5cf5-460e-bd06-fe763287363c','CASH','IN_DELIVERY','1200','e3fca320-3dd4-42ce-80f1-1fe7d9f1d6aa','3836cbf0-ec20-47ae-9f2f-4a59b89c0184');

INSERT INTO offers(id, description, discount,start_date,  end_date)VALUES ('a1e611b8-5cf5-460e-bd06-fe763287363c', 'test discount','1.9', '2021-09-29', '2021-08-29');

INSERT INTO products(product_id,created,family,image,modified,name,price,stock,vat,wholesale_price,wholesale_quantity,shop_id, offer_id ) VALUES('1','1626853613388','Drinks','url image','1626853613388','water','150','100','21','100','200','e74bca4d-fab2-43f0-b372-92c408fc02cb','a1e611b8-5cf5-460e-bd06-fe763287363c' );
INSERT INTO products(product_id,created,family,image,modified,name,price,stock,vat,wholesale_price,wholesale_quantity,shop_id, offer_id) VALUES('2','1626853613388','Food','url image','1626853613388','pizza','250','200','21','175','200','e74bca4d-fab2-43f0-b372-92c408fc02cb','a1e611b8-5cf5-460e-bd06-fe763287363c' );
INSERT INTO products(product_id,created,family,image,modified,name,price,stock,vat,wholesale_price,wholesale_quantity,shop_id) VALUES('3','1626853613388','Food','url image','1626853613388','pasta','350','50','21','250','100',null );
INSERT INTO products(product_id,created,family,image,modified,name,price,stock,vat,wholesale_price,wholesale_quantity,shop_id) VALUES('4','1626853613388','Desert','imagen nº4','1626853613388','flan','0','0','21','0','0','89bc2321-50f5-4592-9870-45db8300b141');
INSERT INTO products(product_id,created,family,image,modified,name,price,stock,vat,wholesale_price,wholesale_quantity,shop_id) VALUES('5','1626853613388','Desert','imagen nº5','1626853613388','icecream','0','0','21','0','0','89bc2321-50f5-4592-9870-45db8300b141');
INSERT INTO products(product_id,created,family,image,modified,name,price,stock,vat,wholesale_price,wholesale_quantity,shop_id) VALUES('6','1626853613388','Desert','imagen nº6','1626853613388','yoghurt','0','0','21','0','0','89bc2321-50f5-4592-9870-45db8300b141');
INSERT INTO products(product_id,created,family,image,modified,name,price,stock,vat,wholesale_price,wholesale_quantity,shop_id) VALUES('7','1626853613388','Food','imagen nº7','1626853613388','bread','0','0','21','0','0','89bc2321-50f5-4592-9870-45db8300b141');
INSERT INTO products(product_id,created,family,image,modified,name,price,stock,vat,wholesale_price,wholesale_quantity,shop_id) VALUES('8','1626853613388','Food','imagen nº8','1626853613388','rice','0','0','21','0','0','89bc2321-50f5-4592-9870-45db8300b141');
INSERT INTO products(product_id,created,family,image,modified,name,price,stock,vat,wholesale_price,wholesale_quantity,shop_id) VALUES('9','1626853613388','Desert','imagen nº9','1626853613388','ejemplo 9','0','0','21','0','0','89bc2321-50f5-4592-9870-45db8300b141');
INSERT INTO products(product_id,created,family,image,modified,name,price,stock,vat,wholesale_price,wholesale_quantity,shop_id) VALUES('10','1626853613388','Desert','imagen nº10','1626853613388','ejemplo 10','0','0','21','0','0','89bc2321-50f5-4592-9870-45db8300b141');
INSERT INTO products(product_id,created,family,image,modified,name,price,stock,vat,wholesale_price,wholesale_quantity,shop_id) VALUES('11','1626853613388','Desert','imagen nº11','1626853613388','ejemplo 11','0','0','21','0','0','89bc2321-50f5-4592-9870-45db8300b141');
INSERT INTO products(product_id,created,family,image,modified,name,price,stock,vat,wholesale_price,wholesale_quantity,shop_id) VALUES('12','1626853613388','Desert','imagen nº12','1626853613388','ejemplo 12','0','0','21','0','0','89bc2321-50f5-4592-9870-45db8300b141');

INSERT INTO order_details( id,quantity,subtotal,order_id,product_id)VALUES('5400c1b6-2766-4d44-a5ed-bad9e5f635db','2','700','9ebcaeaf-4b2f-48de-8a4e-dafa630965a6',3);
INSERT INTO order_details( id,quantity,subtotal,order_id,product_id)VALUES('66f2e44c-c7bc-43b9-9ce6-043a01888b8b','1','150','28fcaf2e-91c1-4e38-98e7-69398feca6b2',1);
INSERT INTO order_details( id,quantity,subtotal,order_id,product_id)VALUES('87add73c-bf07-4cba-a63f-bd1f2a31b805','2','500','9ebcaeaf-4b2f-48de-8a4e-dafa630965a6',2);
INSERT INTO order_details( id,quantity,subtotal,order_id,product_id)VALUES('b98b8986-b545-426c-bb63-353bd0887715','1','150','173e9e5a-f489-49f4-a502-38b29f6e014d',1);
INSERT INTO order_details( id,quantity,subtotal,order_id,product_id)VALUES('bd101b7a-1581-4367-8d49-8d2683854476','2','300','0f010722-7425-4d6d-8a48-ac6ec38bdcf6',1);
INSERT INTO order_details( id,quantity,subtotal,order_id,product_id)VALUES('c939dd14-b540-49fb-82e3-c236c97911cc','3','1050','28fcaf2e-91c1-4e38-98e7-69398feca6b2',3);
INSERT INTO order_details( id,quantity,subtotal,order_id,product_id)VALUES('d978a6e2-948b-451b-b6a1-91c377e70def','1','250','0f010722-7425-4d6d-8a48-ac6ec38bdcf6',2);
INSERT INTO order_details( id,quantity,subtotal,order_id,product_id)VALUES('fc3aa971-74dc-4424-ac33-c3155eb254ce','3','1050','173e9e5a-f489-49f4-a502-38b29f6e014d',3);






