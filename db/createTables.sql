CREATE TABLE IF NOT EXISTS home_made_meal (
                 id CHAR(36) NOT NULL,
                 user_id VARCHAR(36) NOT NULL,
                 name VARCHAR(200) NOT NULL,
                 photo_url VARCHAR(200) DEFAULT NULL,
                 duration_in_minutes INT(11) DEFAULT NULL,
                 PRIMARY KEY (id)
               ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS outside_meal (
                 id CHAR(36) NOT NULL,
                 user_id VARCHAR(36) NOT NULL,
                 name VARCHAR(200) NOT NULL,
                 photo_url VARCHAR(200) DEFAULT NULL,
                 duration_in_minutes INT(11) DEFAULT NULL,
                 restaurant_name VARCHAR(200) NOT NULL,
                 price DECIMAL(4,2) NOT NULL,
                 last_eaten_date DATE DEFAULT NULL,
                 PRIMARY KEY (id)
               ) ENGINE=InnoDB;