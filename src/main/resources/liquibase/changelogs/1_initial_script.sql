CREATE TABLE IF NOT EXISTS `user` (
  `username` VARCHAR(50) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `password` VARCHAR(200) NOT NULL,
  `name` VARCHAR(50) NULL DEFAULT NULL,
  `surname` VARCHAR(50) NULL DEFAULT NULL,
  `enabled` TINYINT(1) NOT NULL,
  PRIMARY KEY (`username`));

CREATE TABLE IF NOT EXISTS `currency` (
  `name` VARCHAR(50) NOT NULL,
  `code` VARCHAR(50) NOT NULL,
  `number` INT(11) NULL DEFAULT NULL,
  `symbol` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`code`));

CREATE TABLE IF NOT EXISTS `account` (
  `account_id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `type` VARCHAR(50) NOT NULL,
  `summary` DECIMAL(18,2) NOT NULL,
  `description` VARCHAR(300) NOT NULL,
  `username` VARCHAR(50) NOT NULL,
  `currency_code` VARCHAR(50) NOT NULL DEFAULT 'USD',
  `synchronization_token` VARCHAR(50) NULL DEFAULT NULL,
  `synchronization_id` VARCHAR(50) NULL DEFAULT NULL,
  `synchronization_time` BIGINT(20) NULL DEFAULT NULL,
  CONSTRAINT `fk_account_user`
    FOREIGN KEY (`username`)
    REFERENCES `user` (`username`),
  CONSTRAINT `fk_currency_code__account_id`
    FOREIGN KEY (`currency_code`)
    REFERENCES `currency` (`code`));


CREATE TABLE IF NOT EXISTS `authority` (
  `username` VARCHAR(50) NOT NULL,
  `authority` VARCHAR(50) NOT NULL,
  CONSTRAINT `fk_authority_user`
    FOREIGN KEY (`username`)
    REFERENCES `user` (`username`));


CREATE TABLE IF NOT EXISTS `category` (
  `category_id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  `username` VARCHAR(50) NOT NULL,
  `income` TINYINT(1) NULL DEFAULT '0',
  CONSTRAINT `fk_user_id`
    FOREIGN KEY (`username`)
    REFERENCES `user` (`username`));


CREATE TABLE IF NOT EXISTS `expense` (
  `expense_id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `sum` DECIMAL(18,2) NOT NULL,
  `category_id` INT(11) NOT NULL,
  `date` DATE NULL DEFAULT NULL,
  `time` TIME NOT NULL,
  `comment` VARCHAR(50) NULL DEFAULT NULL,
  `username` VARCHAR(50) NOT NULL,
  `account_id` INT(11) NOT NULL,
  `currency_code` VARCHAR(50) NOT NULL DEFAULT 'USD',
  CONSTRAINT `fk_account_id`
    FOREIGN KEY (`account_id`)
    REFERENCES `account` (`account_id`),
  CONSTRAINT `fk_currency_code_expense_id`
    FOREIGN KEY (`currency_code`)
    REFERENCES `currency` (`code`),
  CONSTRAINT `fk_expense_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `category` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_expense_user`
    FOREIGN KEY (`username`)
    REFERENCES `user` (`username`));

CREATE TABLE IF NOT EXISTS `mcc` (
  `mcc_id` INT(11) NOT NULL,
  `description` VARCHAR(300) NULL DEFAULT NULL,
  `category_name` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`mcc_id`));





