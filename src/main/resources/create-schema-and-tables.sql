CREATE DATABASE IF NOT EXISTS locking_demo CHARSET utf8mb4 COLLATE utf8mb4_croatian_ci;

CREATE TABLE IF NOT EXISTS `item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` text COLLATE utf8mb4_croatian_ci NOT NULL,
  `name` varchar(128) COLLATE utf8mb4_croatian_ci NOT NULL,
  `price` double DEFAULT NULL,
  `processed_at` datetime DEFAULT NULL,
  `status` varchar(12) COLLATE utf8mb4_croatian_ci DEFAULT 'CREATED',
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;