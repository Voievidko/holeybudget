DELETE FROM expense WHERE username = 'demo';
DELETE FROM category WHERE username = 'demo';
DELETE FROM account WHERE username = 'demo';
DELETE FROM authority WHERE username = 'demo';
DELETE FROM user WHERE username = 'demo';

INSERT INTO user (username, email, password, name, surname, enabled) VALUES
    ('demo', 'demo@demo.com', '$2a$10$iQXJ7lGfanJdq8.qUEoRq.US4MZzlA4at5XHfzgqQWICp8hQVZp9O', 'Demo', 'Demonov', 1);
INSERT INTO authority (username, authority) VALUES ('demo', 'ROLE_USER');

INSERT INTO account (type, summary, description, username, currency_code) VALUES
    ('Cash', 0.0, 'Cash in wallet', 'demo', 'UAH'),
    ('Debit Card', 0.0, 'Salary debit card', 'demo', 'UAH'),
    ('USD Deposit', 9660.45, 'Foreign Currency Deposit', 'demo', 'USD');

INSERT INTO category (name, description, username, income) VALUES
    ('Salary', 'Salary category', 'demo', 1),
    ('Gifts', 'Gifts category', 'demo', 1),
    ('Deposit Profit', '', 'demo', 1),
    ('Other', '', 'demo', 1),
    ('Apartment Rent', '', 'demo', 0),
    ('Food', 'Food category', 'demo', 0),
    ('Health Care', '', 'demo', 0),
    ('Mobile and Internet', '', 'demo', 0),
    ('Education', '', 'demo', 0),
    ('Presents', '', 'demo', 0),
    ('Amusement', '', 'demo', 0),
    ('Clothes', '', 'demo', 0),
    ('Travel', '', 'demo', 0);

INSERT INTO expense (sum, category_id, date, time, comment, username, account_id, currency_code) VALUES
    (
        48.30,
        (SELECT category_id FROM category WHERE name = 'Deposit Profit'),
        '2020-01-02',
        '10:00:00',
        'Deposit profit',
        'demo',
        (SELECT account_id FROM account WHERE type = 'USD Deposit'),
        'USD'
    ),
    (
        48.54,
        (SELECT category_id FROM category WHERE name = 'Deposit Profit'),
        '2020-02-01',
        '12:32:13',
        'Deposit profit',
        'demo',
        (SELECT account_id FROM account WHERE type = 'USD Deposit'),
        'USD'
    ),
    (
        48.79,
        (SELECT category_id FROM category WHERE name = 'Deposit Profit'),
        '2020-03-01',
        '16:02:54',
        'Deposit profit',
        'demo',
        (SELECT account_id FROM account WHERE type = 'USD Deposit'),
        'USD'
    ),
    (
        49.03,
        (SELECT category_id FROM category WHERE name = 'Deposit Profit'),
        '2020-04-01',
        '07:50:50',
        'Deposit profit',
        'demo',
        (SELECT account_id FROM account WHERE type = 'USD Deposit'),
        'USD'
    ),
    (
        49.28,
        (SELECT category_id FROM category WHERE name = 'Deposit Profit'),
        '2020-05-01',
        '22:14:43',
        'Deposit profit',
        'demo',
        (SELECT account_id FROM account WHERE type = 'USD Deposit'),
        'USD'
    ),
    (
        49.52,
        (SELECT category_id FROM category WHERE name = 'Deposit Profit'),
        '2020-06-01',
        '10:34:23',
        'Deposit profit',
        'demo',
        (SELECT account_id FROM account WHERE type = 'USD Deposit'),
        'USD'
    ),
    (
        49.77,
        (SELECT category_id FROM category WHERE name = 'Deposit Profit'),
        '2020-07-01',
        '13:10:30',
        'Deposit profit',
        'demo',
        (SELECT account_id FROM account WHERE type = 'USD Deposit'),
        'USD'
    ),
    (
        50.02,
        (SELECT category_id FROM category WHERE name = 'Deposit Profit'),
        '2020-08-01',
        '14:04:09',
        'Deposit profit',
        'demo',
        (SELECT account_id FROM account WHERE type = 'USD Deposit'),
        'USD'
    ),
    (
        50.27,
        (SELECT category_id FROM category WHERE name = 'Deposit Profit'),
        '2020-09-01',
        '14:24:36',
        'Deposit profit',
        'demo',
        (SELECT account_id FROM account WHERE type = 'USD Deposit'),
        'USD'
    ),
    (
        50.52,
        (SELECT category_id FROM category WHERE name = 'Deposit Profit'),
        '2020-10-01',
        '11:23:40',
        'Deposit profit',
        'demo',
        (SELECT account_id FROM account WHERE type = 'USD Deposit'),
        'USD'
    ),
    (
        50.77,
        (SELECT category_id FROM category WHERE name = 'Deposit Profit'),
        '2020-11-01',
        '09:17:48',
        'Deposit profit',
        'demo',
        (SELECT account_id FROM account WHERE type = 'USD Deposit'),
        'USD'
    ),
    (
        51.03,
        (SELECT category_id FROM category WHERE name = 'Deposit Profit'),
        '2020-12-01',
        '11:11:11',
        'Deposit profit',
        'demo',
        (SELECT account_id FROM account WHERE type = 'USD Deposit'),
        'USD'
    );

UPDATE account SET summary = (summary + 595.84) WHERE type = 'USD Deposit';

INSERT INTO expense (sum, category_id, date, time, comment, username, account_id, currency_code) VALUES
    (
        53500,
        (SELECT category_id FROM category WHERE name = 'Salary'),
        '2020-07-05',
        '09:30:13',
        'Salary for June',
        'demo',
        (SELECT account_id FROM account WHERE type = 'Debit Card'),
        'UAH'
    ),
    (
        53500,
        (SELECT category_id FROM category WHERE name = 'Salary'),
        '2020-08-04',
        '09:23:17',
        'Salary for July',
        'demo',
        (SELECT account_id FROM account WHERE type = 'Debit Card'),
        'UAH'
    ),
    (
        53500,
        (SELECT category_id FROM category WHERE name = 'Salary'),
        '2020-09-05',
        '09:31:15',
        'Salary for August',
        'demo',
        (SELECT account_id FROM account WHERE type = 'Debit Card'),
        'UAH'
    ),
    (
        53500,
        (SELECT category_id FROM category WHERE name = 'Salary'),
        '2020-10-05',
        '09:37:03',
        'Salary for September',
        'demo',
        (SELECT account_id FROM account WHERE type = 'Debit Card'),
        'UAH'
    ),
    (
        53500,
        (SELECT category_id FROM category WHERE name = 'Salary'),
        '2020-11-03',
        '09:00:33',
        'Salary for October',
        'demo',
        (SELECT account_id FROM account WHERE type = 'Debit Card'),
        'UAH'
    ),
    (
        53500,
        (SELECT category_id FROM category WHERE name = 'Salary'),
        '2020-12-06',
        '10:03:44',
        'Salary for November',
        'demo',
        (SELECT account_id FROM account WHERE type = 'Debit Card'),
        'UAH'
    ),
    (
        96300,
        (SELECT category_id FROM category WHERE name = 'Salary'),
        '2021-01-06',
        '09:22:34',
        'Salary for December + bonus',
        'demo',
        (SELECT account_id FROM account WHERE type = 'Debit Card'),
        'UAH'
    );

UPDATE account SET summary = (summary + 417300) WHERE type = 'Debit Card';
UPDATE account SET summary = (summary - 100000) WHERE type = 'Debit Card';
UPDATE account SET summary = (summary + 100000) WHERE type = 'Cash';

INSERT INTO expense (sum, category_id, date, time, comment, username, account_id, currency_code) VALUES
    (
        12300,
        (SELECT category_id FROM category WHERE name = 'Presents'),
        '2020-12-15',
        '14:30:53',
        'Christmas presents for whole family',
        'demo',
        (SELECT account_id FROM account WHERE type = 'Debit Card'),
        'UAH'
    ),
    (
        530.50,
        (SELECT category_id FROM category WHERE name = 'Food'),
        '2021-01-05',
        '11:28:00',
        'Vegetables and fruit on local market',
        'demo',
        (SELECT account_id FROM account WHERE type = 'Cash'),
        'UAH'
    ),
    (
        11700,
        (SELECT category_id FROM category WHERE name = 'Apartment Rent'),
        '2021-01-02',
        '12:14:10',
        'Apartment rent',
        'demo',
        (SELECT account_id FROM account WHERE type = 'Cash'),
        'UAH'
    ),
    (
        500,
        (SELECT category_id FROM category WHERE name = 'Clothes'),
        '2021-01-08',
        '11:22:10',
        'New t-shirt',
        'demo',
        (SELECT account_id FROM account WHERE type = 'Cash'),
        'UAH'
    ),
    (
        24856,
        (SELECT category_id FROM category WHERE name = 'Travel'),
        '2021-01-07',
        '19:12:19',
        'Tickets booking',
        'demo',
        (SELECT account_id FROM account WHERE type = 'Cash'),
        'UAH'
    ),
    (
        1100,
        (SELECT category_id FROM category WHERE name = 'Health Care'),
        '2021-01-11',
        '09:47:39',
        'Vaccination against coronavirus',
        'demo',
        (SELECT account_id FROM account WHERE type = 'Cash'),
        'UAH'
    ),
    (
        390,
        (SELECT category_id FROM category WHERE name = 'Mobile and Internet'),
        '2021-01-03',
        '19:47:39',
        'Internet',
        'demo',
        (SELECT account_id FROM account WHERE type = 'Cash'),
        'UAH'
    ),
    (
        298.63,
        (SELECT category_id FROM category WHERE name = 'Education'),
        '2021-01-04',
        '15:03:04',
        'Spring Boot course on Udemy',
        'demo',
        (SELECT account_id FROM account WHERE type = 'Debit Card'),
        'UAH'
    );

UPDATE account SET summary = (summary - 12598.63) WHERE type = 'Debit Card';
UPDATE account SET summary = (summary - 39076.5) WHERE type = 'Cash';