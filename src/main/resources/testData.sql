insert into `user` (`user_id`, `email`, `password`, `name`, `surname`) values (1, "petrenko90@gmail.com", "123456", "Mike", "Petrenko");
insert into `user` (`user_id`, `email`, `password`, `name`, `surname`) values (2, "bembo1982@gmail.com", "secret", "Johny", "Patrony");
insert into `account` (`account_id`, `type`, `summary`, `description`, `user_id`) values (1, "Privatbank", 20000, "Credit card", 1);
insert into `account` (`account_id`, `type`, `summary`, `description`, `user_id`) values (2, "UkrSubbank", 55000, "Salary card", 1);
insert into `category` (`category_id`, `name`, `description`) values (1, "Transport", "My transport expenses");
insert into `category` (`category_id`, `name`, `description`) values (2, "Fun", null);
insert into `category` (`category_id`, `name`, `description`) values (3, "Entertainment", "Cafes, bars");


insert into `account` (`account_id`, `type`, `summary`, `description`, `user_id`) values (10, "BankForUser2", 55000, "Salary card", 2);