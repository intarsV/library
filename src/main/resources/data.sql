insert into AUTHOR values
 (1, 'Rainis'),
 (2,'R.Blaumanis'),
 (3,'Viktors Igo');

insert into book values(1, 4, 1, 'Zelta zirgs', 1),
(2, 2, 1, 'Super book', 1),
(3, 2, 0, 'Naves ena', 2),
(4, 4, 1, 'Le miserables', 3);

insert into user values(1, 'xxx', 'intars'),
(2, 'yyy', 'ritvars');

insert into RESERVATION values(1,  NOW(), TRUE,1,1),
(2,  NOW(), TRUE,4,1);