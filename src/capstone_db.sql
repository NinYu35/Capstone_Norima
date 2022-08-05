CREATE DATABASE IF NOT EXISTS `capstone_db`;
USE `capstone_db`;

CREATE TABLE IF NOT EXISTS `customer_acct`(
 `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
 `account_no` varchar(50) NOT NULL,
 `first_name` varchar(50) NOT NULL,
 `last_name` varchar(50) NOT NULL,
 `address` varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS `policy` (
  `id` int NOT NULL AUTO_INCREMENT,
  `policy_no` varchar(50) NOT NULL,
  `customer_acc_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0',
  `policy_holder_uuid` binary(36) DEFAULT NULL,
  `effective_date` date DEFAULT NULL,
  `expiry_date` date DEFAULT NULL,
  `status` varchar(50) NOT NULL,
  `cost` varchar(50) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ;

CREATE TABLE IF NOT EXISTS `policy_holder` (
  `uuid` char(36) NOT NULL,
  `policy_no` varchar(50) NOT NULL,
  `customer_acc_no` varchar(50) NOT NULL,
  `acc_type` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `date_of_birth` date NOT NULL,
  `address` varchar(50) NOT NULL,
  `drivers_license_num` varchar(50) NOT NULL,
  `drivers_license_issued` date NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`)
);

CREATE TABLE IF NOT EXISTS `claim` (
  `id` int NOT NULL AUTO_INCREMENT,
  `policy_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `claim_no` varchar(50) NOT NULL,
  `date_of_accident` date NOT NULL,
  `address_of_accident` varchar(50) NOT NULL,
  `desc_of_accident` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `desc_vehicle_damage` text,
  `est_cost_repairs` double NOT NULL DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `vehicle` (
  `uuid` char(36) NOT NULL,
  `customer_acc_no` varchar(50) NOT NULL DEFAULT '',
  `policy_holder_uuid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `policy_no` varchar(50) NOT NULL DEFAULT '',
  `make` varchar(50) NOT NULL,
  `model` varchar(50) NOT NULL,
  `year` int NOT NULL,
  `type` varchar(50) NOT NULL,
  `fuel_type` varchar(50) NOT NULL,
  `color` varchar(50) NOT NULL,
  `purchase_price` double NOT NULL DEFAULT '0',
  `premium_charged` double NOT NULL DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`)
);
