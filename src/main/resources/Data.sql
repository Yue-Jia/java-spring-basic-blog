
insert into User (username,name,email, password)
values ('EliDgia','yue','yuejia070@gmail.com', '$2a$10$TjUCwf/8bJlxrhOTykjUqO36c8lMy9pgLKHqcnh1S3pZ.q4PxxTwu');





insert into Role (role_name)
values ('ROLE_OWNER');
insert into Role (role_name)
values ('ROLE_CLIENT');
insert into Role (role_name)
values ('ROLE_OAUTH');



insert into user_role (user_id, role_id)
values (1, 1);
insert into user_role (user_id, role_id)
values (1, 2);
insert into user_role (user_id, role_id)
values (1, 3);

