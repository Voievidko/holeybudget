ALTER TABLE category ADD user_id INT(11) NOT NULL;
ALTER TABLE category ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES `user`(`user_id`);