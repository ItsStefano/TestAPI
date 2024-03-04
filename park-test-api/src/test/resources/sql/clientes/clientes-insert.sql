insert into USUARIOS (id, username, password, role) values (100,'romeo@test.com', '$2a$12$n6iriMrp9A2vX/2rEZJVQOxpxlscHP/AhkOsNsnG9gdrI65NjF2Pq', 'ROLE_ADMIN');
insert into USUARIOS (id, username, password, role) values (101,'peto@test.com', '$2a$12$n6iriMrp9A2vX/2rEZJVQOxpxlscHP/AhkOsNsnG9gdrI65NjF2Pq', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role) values (102,'riri@test.com', '$2a$12$n6iriMrp9A2vX/2rEZJVQOxpxlscHP/AhkOsNsnG9gdrI65NjF2Pq', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role) values (103,'lennon@test.com', '$2a$12$n6iriMrp9A2vX/2rEZJVQOxpxlscHP/AhkOsNsnG9gdrI65NjF2Pq', 'ROLE_CLIENTE');

insert into CLIENTES (id, nome, cpf, id_usuario) values (10, 'Peto Stefano', '40591050072', 101);
insert into CLIENTES (id, nome, cpf, id_usuario) values (20, 'Riri Stefano', '71490768076', 102);