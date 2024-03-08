insert into USUARIOS (id, username, password, role) values (100,'romeo@test.com', '$2a$12$n6iriMrp9A2vX/2rEZJVQOxpxlscHP/AhkOsNsnG9gdrI65NjF2Pq', 'ROLE_ADMIN');
insert into USUARIOS (id, username, password, role) values (101,'peto@test.com', '$2a$12$n6iriMrp9A2vX/2rEZJVQOxpxlscHP/AhkOsNsnG9gdrI65NjF2Pq', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role) values (102,'riri@test.com', '$2a$12$n6iriMrp9A2vX/2rEZJVQOxpxlscHP/AhkOsNsnG9gdrI65NjF2Pq', 'ROLE_CLIENTE');

insert into VAGAS (id, codigo, status) values (10, 'A-01', 'LIVRE');
insert into VAGAS (id, codigo, status) values (20, 'A-02', 'LIVRE');
insert into VAGAS (id, codigo, status) values (30, 'A-03', 'OCUPADA');
insert into VAGAS (id, codigo, status) values (40, 'A-04', 'LIVRE');