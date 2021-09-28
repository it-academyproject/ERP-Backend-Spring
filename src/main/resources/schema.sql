DROP DATABASE IF EXISTS proyectoerp;
CREATE DATABASE proyectoerp;
USE proyectoerp;

CREATE TABLE `addresses` (
  `id` varchar(255) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `zip_code` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `categories` (
  `category_id` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `offer_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `clients` (
  `id` varchar(255) NOT NULL,
  `dni` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name_surname` varchar(255) DEFAULT NULL,
  `address_id` varchar(255) NOT NULL,
  `shipping_address_id` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `employees` (
  `id` varchar(255) NOT NULL,
  `dni` varchar(255) DEFAULT NULL,
  `in_date` date NOT NULL,
  `name` varchar(255) NOT NULL,
  `out_date` date DEFAULT NULL,
  `phone` int(11) NOT NULL,
  `salary` double NOT NULL,
  `surname` varchar(255) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `offers` (
  `offer_id` varchar(255) NOT NULL,
  `discount` double DEFAULT NULL,
  `ends_on` datetime DEFAULT NULL,
  `free_quantity` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `paid_quantity` int(11) DEFAULT NULL,
  `starts_on` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `orders` (
  `order_id` varchar(255) NOT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `employee_id` varchar(255) DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `billing_address_id` varchar(255) DEFAULT NULL,
  `shipping_address_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `order_details` (
  `id` varchar(255) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `subtotal` double DEFAULT NULL,
  `order_id` varchar(255) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `products` (
  `product_id` int(11) NOT NULL,
  `created` bigint(20) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `modified` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `vat` double DEFAULT NULL,
  `wholesale_price` double DEFAULT NULL,
  `wholesale_quantity` int(11) DEFAULT NULL,
  `category_id` varchar(255) DEFAULT NULL,
  `offer_id` varchar(255) DEFAULT NULL,
  `shop_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `shops` (
  `shop_id` varchar(255) NOT NULL,
  `brand_name` varchar(50) NOT NULL,
  `company_name` varchar(50) NOT NULL,
  `creation_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `nif` varchar(50) NOT NULL,
  `phone` int(11) NOT NULL,
  `web_address` varchar(255) DEFAULT NULL,
  `address_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `account_locked` tinyint(1) NOT NULL DEFAULT 0,
  `active` tinyint(1) NOT NULL DEFAULT 1,
  `failed_login_attempts` int(11) DEFAULT 0,
  `last_session` datetime DEFAULT NULL,
  `lock_time` datetime DEFAULT NULL,
  `password` varchar(106) DEFAULT NULL,
  `registration_date` datetime DEFAULT NULL,
  `user_type` varchar(16) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `working_hours` (
  `date` date NOT NULL,
  `employee_id` varchar(255) NOT NULL,
  `check_in` time DEFAULT NULL,
  `check_out` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `addresses`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_id`),
  ADD UNIQUE KEY `UK_dpusovwfttipdju5vdp10kcbm` (`description`),
  ADD UNIQUE KEY `UK_t8o6pivur7nn124jehx7cygw5` (`name`),
  ADD KEY `FKqxt8f68cbnwltlnnts4jk6tp2` (`offer_id`);

ALTER TABLE `clients`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_anrflk31tsvd26gkrnt6booj1` (`address_id`),
  ADD UNIQUE KEY `UK_smrp6gi0tckq1w5rnd7boyowu` (`user_id`),
  ADD UNIQUE KEY `UK_7lb86p1hnr0kauij7t55krebo` (`dni`),
  ADD KEY `FKdc6klo8mv7nq7gy9d6io75xvg` (`shipping_address_id`);

ALTER TABLE `employees`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_j2dmgsma6pont6kf7nic9elpd` (`user_id`);

ALTER TABLE `offers`
  ADD PRIMARY KEY (`offer_id`);

ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `FK66jolu65brloux12yi37qy3ky` (`billing_address_id`),
  ADD KEY `FKmk6q95x8ffidq82wlqjaq7sqc` (`shipping_address_id`);

ALTER TABLE `order_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjyu2qbqt8gnvno9oe9j2s2ldk` (`order_id`),
  ADD KEY `FK4q98utpd73imf4yhttm3w0eax` (`product_id`);

ALTER TABLE `products`
  ADD PRIMARY KEY (`product_id`),
  ADD UNIQUE KEY `UK_o61fmio5yukmmiqgnxf8pnavn` (`name`),
  ADD KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  ADD KEY `FKilcfftshp3ev9hycesd6grw2v` (`offer_id`),
  ADD KEY `FK7kp8sbhxboponhx3lxqtmkcoj` (`shop_id`);

ALTER TABLE `shops`
  ADD PRIMARY KEY (`shop_id`),
  ADD UNIQUE KEY `UK_9k3bfoc65784xov7nfks53918` (`address_id`);

ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`);

ALTER TABLE `working_hours`
  ADD PRIMARY KEY (`date`,`employee_id`);

ALTER TABLE `products`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

ALTER TABLE `categories`
  ADD CONSTRAINT `FKqxt8f68cbnwltlnnts4jk6tp2` FOREIGN KEY (`offer_id`) REFERENCES `offers` (`offer_id`);

ALTER TABLE `clients`
  ADD CONSTRAINT `FK21gyuophuha3vq8t1os4x2jtl` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`),
  ADD CONSTRAINT `FKdc6klo8mv7nq7gy9d6io75xvg` FOREIGN KEY (`shipping_address_id`) REFERENCES `addresses` (`id`),
  ADD CONSTRAINT `FKtiuqdledq2lybrds2k3rfqrv4` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `employees`
  ADD CONSTRAINT `FK69x3vjuy1t5p18a5llb8h2fjx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `orders`
  ADD CONSTRAINT `FK66jolu65brloux12yi37qy3ky` FOREIGN KEY (`billing_address_id`) REFERENCES `addresses` (`id`),
  ADD CONSTRAINT `FKmk6q95x8ffidq82wlqjaq7sqc` FOREIGN KEY (`shipping_address_id`) REFERENCES `addresses` (`id`);

ALTER TABLE `order_details`
  ADD CONSTRAINT `FK4q98utpd73imf4yhttm3w0eax` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  ADD CONSTRAINT `FKjyu2qbqt8gnvno9oe9j2s2ldk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`);

ALTER TABLE `products`
  ADD CONSTRAINT `FK7kp8sbhxboponhx3lxqtmkcoj` FOREIGN KEY (`shop_id`) REFERENCES `shops` (`shop_id`),
  ADD CONSTRAINT `FKilcfftshp3ev9hycesd6grw2v` FOREIGN KEY (`offer_id`) REFERENCES `offers` (`offer_id`),
  ADD CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`);

ALTER TABLE `shops`
  ADD CONSTRAINT `FK5kd1hexorlofv8mdrktpe5q6b` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`);
COMMIT;




