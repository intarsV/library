

insert into AUTHOR values
 (1, 'Rainis'),
 (2,'R.Blaumanis'),
 (3,'Viktors Igo');

insert into BOOK values(1,7 ,7,1 , 'Zelta zirgs', 1),
(2, 5 ,5, 2, 'Super book' ,1),
(3, 9,9, 2, 'Nāves enā', 2),
(4,3 ,3,0 , 'Le Miserables', 3);

insert into authority values (1, 'ADMIN'),(2,'USER');

insert into users values(1, 1, '$2a$10$b5Ehdr5IysRZvOsUbHN2GuaPsN5zPKTr95FOli8gzWEo91YCCcX3K', 'initex');

insert into user_authorities  values(1, 1);

--insert into RESERVATION values(1,NOW(), TRUE,1, 1 ),
-- (2, NOW(), TRUE,4,1), (3,NOW(), FALSE, 4, 1 ),
--  (4,NOW(), FALSE,2, 2  ), (5,NOW(), FALSE,  1, 2);