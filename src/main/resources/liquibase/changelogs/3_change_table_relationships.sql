USE yourfoundsdb;
ALTER TABLE `user` DROP FOREIGN KEY fk_user_expense;
ALTER TABLE `user` DROP FOREIGN KEY fk_user_account1;
ALTER TABLE `user` DROP COLUMN expense_id;
ALTER TABLE `user` DROP COLUMN account_id;


ALTER TABLE `account` ADD COLUMN user_id INT NOT NULL;
ALTER TABLE `account`
ADD CONSTRAINT fk_account_user
FOREIGN KEY (user_id) REFERENCES `user`(user_id);

ALTER TABLE `expense` ADD COLUMN user_id INT NOT NULL;
ALTER TABLE `expense`
ADD CONSTRAINT fk_expense_user
FOREIGN KEY (user_id) REFERENCES `user`(user_id);