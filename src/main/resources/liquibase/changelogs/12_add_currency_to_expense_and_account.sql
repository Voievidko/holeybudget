ALTER TABLE notspenddb.expense ADD currency_code VARCHAR(50) NOT NULL DEFAULT 'USD';
ALTER TABLE notspenddb.expense ADD CONSTRAINT fk_currency_code_expense_id FOREIGN KEY (`currency_code`) REFERENCES `currency`(`code`);

ALTER TABLE notspenddb.account ADD currency_code VARCHAR(50) NOT NULL DEFAULT 'USD';
ALTER TABLE notspenddb.account ADD CONSTRAINT fk_currency_code__account_id FOREIGN KEY (`currency_code`) REFERENCES `currency`(`code`);