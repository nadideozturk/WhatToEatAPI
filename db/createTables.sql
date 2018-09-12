ALTER SCHEMA `WhatToEatLocal`  DEFAULT CHARACTER SET utf8;

CREATE TABLE `home_made_meal` (
  `id` char(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `name` varchar(200) NOT NULL,
  `photo_url` varchar(200) DEFAULT NULL,
  `duration_in_minutes` int(11) DEFAULT NULL,
  `last_eaten_date` date DEFAULT NULL,
  `cat_id` char(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `home_made_meal_diary` (
  `id` char(36) NOT NULL,
  `hmmeal_id` char(36) NOT NULL,
  `eaten_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `outside_meal` (
  `id` char(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `name` varchar(200) NOT NULL,
  `photo_url` varchar(200) DEFAULT NULL,
  `last_eaten_date` date DEFAULT NULL,
  `price` decimal(7,2) NOT NULL,
  `restaurant_name` varchar(200) NOT NULL,
  `cat_id` char(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `outside_meal_diary` (
  `id` char(36) NOT NULL,
  `omeal_id` char(36) NOT NULL,
  `eaten_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
