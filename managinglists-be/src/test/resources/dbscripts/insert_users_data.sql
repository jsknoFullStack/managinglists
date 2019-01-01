

select * from `users`;
select * from roles;
select * from `privileges`;
select * from users_roles;

insert into roles (name) values ('USER');
insert into roles (name) values ('ADMIN');

insert into `privileges` (name) values ('READ');
insert into `privileges` (name) values ('WRITE');

insert into roles_privileges (role_id, privilege_id) values (1,1);
insert into roles_privileges (role_id, privilege_id) values (2,1);
insert into roles_privileges (role_id, privilege_id) values (2,2);

insert into users_roles (user_id, role_id) values (1, 2);
insert into users_roles (user_id, role_id) values (2, 1);

select u.id, u.username, r.id as roleID, r.name, p.id as PrivID, p.name
from users u
join users_roles ur on u.id = ur.user_id
join roles r on ur.role_id = r.id
join roles_privileges rp on r.id = rp.role_id
join `privileges` p on rp.privilege_id = p.id;

#delete from users_roles where user_id = 3 and role_id = 1;