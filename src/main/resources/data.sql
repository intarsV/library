
drop table if exists authors, authorities, books, reservations, users;

create table authors(
author_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
author_name VARCHAR(100) NOT NULL,
deleted TINYINT
 );

create table authorities(
authority_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
authority_name VARCHAR(100) NOT NULL,
);

create table books(
book_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
book_title VARCHAR(100) NOT NULL,
book_author BIGINT, foreign key (book_author) references authors(author_id),
book_genre VARCHAR(30),
copies INTEGER,
available INTEGER,
deleted TINYINT
);

create table users(
user_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
user_name VARCHAR(100) NOT NULL,
password VARCHAR(100) NOT NULL,
enabled TINYINT
);

create table reservations (
reservation_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
reservation_date timestamp ,
book BIGINT, foreign key (book) references books(book_id),
user BIGINT, foreign key (user) references users(user_id),
hand_out INTEGER,
returned INTEGER,
deleted TINYINT

);

insert into authors (author_id, author_name, deleted) values
(1, 'Rainis', false),
(2, 'Rūdolfs Blaumanis', false),
(3, 'Viktors Igo', false),
(4, 'Aleksandrs Dimā', false),
(5, 'Džeks Londons', false);

insert into authorities (authority_id, authority_name) values
(1, 'ADMIN'),
(2, 'USER');

insert into users ( user_name, password, enabled) values
( 'initex', '$2a$10$b5Ehdr5IysRZvOsUbHN2GuaPsN5zPKTr95FOli8gzWEo91YCCcX3K', 1),
( 'ivars', '$2a$10$lQ88IYbFzq2u4J6SU9H8per3rr9ufGPnEKW/wXTZxKCTldLHOGrG6', 1),
( 'ritvars', '$2a$10$xW96u/yP/w.oce9SiN36p.DYTXb6Mpg75P15nds6p5OaqFKNj3m9a', 1);

insert into user_authorities values (1, 1), (2, 2), (3, 2);

insert into books ( book_title, book_author, book_genre, copies, available, deleted) values ( 'Zelta zirgs', 2, 'POETRY', 7, 6, false);
insert into books ( book_title, book_author, book_genre, copies, available, deleted) values ( 'Super book', 1, 'NOVEL', 5, 4, false);
insert into books ( book_title, book_author, book_genre, copies, available, deleted) values ( 'Nāves enā', 2, 'PROSE', 9, 9, false );
insert into books ( book_title, book_author, book_genre, copies, available, deleted) values ( 'Le Miserables', 3, 'PROSE', 3, 2, false);
insert into books ( book_title, book_author, book_genre, copies, available, deleted) values ( 'Trīs musketieri', 4, 'NOVEL', 3, 3, false);
insert into books ( book_title, book_author, book_genre, copies, available, deleted) values ( 'Baltais Ilknis', 5, 'NOVEL', 4, 4, false);

insert into reservations (reservation_date, book, user, hand_out, returned, deleted)values ( NOW(), 1, 3, true, true, false);
insert into reservations (reservation_date, book, user, hand_out, returned, deleted)values ( NOW(), 4, 2, true, true, false);
insert into reservations (reservation_date, book, user, hand_out, returned, deleted)values ( NOW(), 4, 3, true, false, false);
insert into reservations (reservation_date, book, user, hand_out, returned, deleted)values ( NOW(), 2, 2, true, false, false);
insert into reservations (reservation_date, book, user, hand_out, returned, deleted)values ( NOW(), 1, 2, true, false, false);
