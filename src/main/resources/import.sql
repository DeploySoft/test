-- Account with amount
insert into account (amount, id) values (500000, '5233021047335897794');
insert into account_config (value, account_id, type_config) values ('3', '5233021047335897794','LIMIT_TRANSFER_PER_DAY');
insert into account_config (value, account_id, type_config) values ('CAD', '5233021047335897794', 'CURRENCY_DEFAULT');
-- Account without amount
insert into account (amount, id) values (0, '8330809137165845524');
insert into account_config (value, account_id, type_config) values ('3', '8330809137165845524','LIMIT_TRANSFER_PER_DAY');
insert into account_config (value, account_id, type_config) values ('CAD', '8330809137165845524', 'CURRENCY_DEFAULT');
