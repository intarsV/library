

insert into AUTHOR values
 (1, 'Rainis'),
 (2,'R.Blaumanis'),
 (3,'Viktors Igo');

insert into BOOK values(1,7 ,1 , 'Zelta zirgs', 1),
(2, 5 , 2, 'Super book' ,1),
(3, 9, 2, 'Naves ena', 2),
(4,3 ,0 , 'Le miserables', 3);

insert into user values(1, 'intars','xxx' ),
(2, 'ritvars', 'yyy');

insert into RESERVATION values(1,NOW(), TRUE,1, 1 ),
 (2, NOW(), TRUE,4,1), (3,NOW(), FALSE, 4, 1 ),
  (4,NOW(), FALSE,2, 2  ), (5,NOW(), FALSE,  1, 2);