ALTER TABLE category ADD username VARCHAR(50) NOT NULL;
ALTER TABLE category ADD CONSTRAINT fk_user_id FOREIGN KEY (username) REFERENCES `user`(`username`);