ALTER TABLE `user`
ADD COLUMN `default_currency` VARCHAR(50),
ADD CONSTRAINT `fk_currency_code` FOREIGN KEY (`default_currency`) REFERENCES `currency` (`code`);
