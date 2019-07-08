insert into `user` (`username`, `email`, `password`, `name`, `surname`, `enabled`) values ("user1", "petrenko90@gmail.com", "$2a$10$HfEA5y7Is6ZIx0lq4Gnq4.E9/jHrqwSPWw5ntLu6XaG.sfQNmwnje", "Mike", "Petrenko", true);
insert into `user` (`username`, `email`, `password`, `name`, `surname`, `enabled`) values ("user2", "bembo1982@gmail.com", "$2a$10$/saHy4o.bCBP6cDDOqbUwuD6B0fZYmaze9iNiAA6tIlkz7FZ1iMpa", "Johny", "Patrony", true);
insert into `account` (`account_id`, `type`, `summary`, `description`, `username`) values (1, "Privatbank", 20000, "Credit card", "user1");
insert into `account` (`account_id`, `type`, `summary`, `description`, `username`) values (2, "UkrSubbank", 55000, "Salary card", "user1");
insert into `category` (`category_id`, `name`, `description`, `username`) values (1, "Transport", "My transport expenses","user1");
insert into `category` (`category_id`, `name`, `description`, `username`) values (2, "Fun", null, "user1");
insert into `category` (`category_id`, `name`, `description`, `username`) values (3, "Entertainment", "Cafes, bars", "user1");

insert into `account` (`account_id`, `type`, `summary`, `description`, `username`) values (10, "BankForUser2", 55000, "Salary card", "user2");

insert into `authority` (`username`, `authority`) values ("user1", "ROLE_ADMIN");
insert into `authority` (`username`, `authority`) values ("user1", "ROLE_USER");
insert into `authority` (`username`, `authority`) values ("user2", "ROLE_USER");

insert into expense (`expense_id`, `sum`, `category_id`, `date`, `time`, `comment`, `username`, `account_id`)
values (9, 200.2, 2, '2019-6-30', '13:59:59.999999', 'some comment', 'user1', 1);

# delete all data
delete from `expense` where expense_id > 0;
delete from `account` where account_id > 0;
delete from `category` where category_id > 0;

delete from `currency` where code != NULL;
delete from `user` where username != NULL;
delete from `authority` where username != NULL;


drop table `expense`;
drop table `account`;
drop table `category`;
drop table `authority`;
drop table `currency`;
drop table `user`;
DROP TABLE `yourfoundsdb`.`databasechangelog`;
DROP TABLE `yourfoundsdb`.`databasechangeloglock`;
