
insert into roles (name) values ('USER');
insert into roles (name) values ('ADMIN');

insert into `privileges` (name) values ('READ');
insert into `privileges` (name) values ('WRITE');

insert into roles_privileges (role_id, privilege_id) values (1,1);
insert into roles_privileges (role_id, privilege_id) values (2,1);
insert into roles_privileges (role_id, privilege_id) values (2,2);