ALTER SCHEMA `WhatToEatLocal`  DEFAULT CHARACTER SET utf8;

CREATE TABLE `user` (
  `id` varchar(36) NOT NULL,
  `name` varchar(45) NOT NULL,
  `photo_url` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `home_made_meal` (
  `id` char(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `name` varchar(200) NOT NULL,
  `photo_url` varchar(200) DEFAULT NULL,
  `duration_in_minutes` int(11) DEFAULT NULL,
  `last_eaten_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_home_made_meal_user_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `home_made_meal_diary` (
  `id` char(36) NOT NULL,
  `hmmeal_id` char(36) NOT NULL,
  `eaten_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_home_made_meal_diary_home_made_meal1_idx` (`hmmeal_id`),
  CONSTRAINT `fk_home_made_meal_diary_home_made_meal1` FOREIGN KEY (`hmmeal_id`) REFERENCES `home_made_meal` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `outside_meal` (
  `id` char(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `name` varchar(200) NOT NULL,
  `photo_url` varchar(200) DEFAULT NULL,
  `duration_on_minutes` int(11) DEFAULT NULL,
  `last_eaten_date` date DEFAULT NULL,
  `price` decimal(7,2) NOT NULL,
  `restaurant_name` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_outside_meal_user1_idx` (`user_id`),
  CONSTRAINT `fk_outside_meal_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `outside_meal_diary` (
  `id` char(36) NOT NULL,
  `omeal_id` char(36) NOT NULL,
  `eaten_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_outside_meal_diary_outside_meal1_idx` (`omeal_id`),
  CONSTRAINT `fk_outside_meal_diary_outside_meal1` FOREIGN KEY (`omeal_id`) REFERENCES `outside_meal` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
