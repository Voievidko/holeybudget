CREATE TABLE authority (username varchar(50) NOT NULL,
                        authority varchar(50) not null,
                        constraint fk_authority_user foreign key(username) references `user`(username)
);