insert into authority 
values (1, 'ROLE_ADMIN');

insert into authority 
values (2, 'ROLE_USER');

insert into user
values(10001,'1995-01-01', 's.cabrerof4@gmail.com', 'Sergio', '$2b$10$3BizH/44iS5kxplnq/2ulutQLx4Yzz7HEIXAXSgMSSQZ3ShGgxwL6', 'Cabrero' , 'admin');

insert into user_authority
values(10001, 1);

insert into user_authority
values(10001, 2);