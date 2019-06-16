ALTER TABLE expense ADD account_id INT(11) NOT NULL;
ALTER TABLE expense ADD CONSTRAINT fk_account_id FOREIGN KEY (account_id) REFERENCES `account`(`account_id`);