
drop table if exists authors, authorities, books, reservations, users;

create table authors(
author_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
author_name VARCHAR(100) NOT NULL,
enabled BOOLEAN
 );
CREATE INDEX author_name ON authors(author_name);

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
enabled BOOLEAN
);
CREATE INDEX book_title ON books(book_title);
CREATE INDEX book_author ON books(book_author);
CREATE INDEX book_genre ON books(book_genre);

create table users(
user_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
user_name VARCHAR(100) NOT NULL,
password VARCHAR(100) NOT NULL,
enabled BOOLEAN
);
CREATE INDEX user_name ON users(user_name);

create table reservations (
reservation_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
reservation_date timestamp ,
book BIGINT, foreign key (book) references books(book_id),
user BIGINT, foreign key (user) references users(user_id),
status VARCHAR(30) NOT NULL,
);
CREATE INDEX book ON reservations(book);
CREATE INDEX user ON reservations(user);
CREATE INDEX status ON reservations(status);

insert into authors (author_id, author_name, enabled) values
(1, 'Rainis', true),
(2, 'Rūdolfs Blaumanis', true),
(3, 'Viktors Igo', true),
(4, 'Aleksandrs Dimā', true),
(5, 'Džeks Londons', true);

insert into authorities (authority_id, authority_name) values
(1, 'ADMIN'),
(2, 'USER');

insert into users ( user_name, password, enabled) values
( 'initex', '$2a$10$b5Ehdr5IysRZvOsUbHN2GuaPsN5zPKTr95FOli8gzWEo91YCCcX3K', true),
( 'ivars', '$2a$10$lQ88IYbFzq2u4J6SU9H8per3rr9ufGPnEKW/wXTZxKCTldLHOGrG6', true),
( 'ritvars', '$2a$10$xW96u/yP/w.oce9SiN36p.DYTXb6Mpg75P15nds6p5OaqFKNj3m9a', true);

insert into user_authorities values (1, 1), (2, 2), (3, 2);

insert into books ( book_title, book_author, book_genre, copies, available, enabled) values ( 'Zelta zirgs', 2, 'POETRY', 7, 6, true);
insert into books ( book_title, book_author, book_genre, copies, available, enabled) values ( 'Super book', 1, 'NOVEL', 5, 4, true);
insert into books ( book_title, book_author, book_genre, copies, available, enabled) values ( 'Nāves enā', 2, 'PROSE', 9, 9, true );
insert into books ( book_title, book_author, book_genre, copies, available, enabled) values ( 'Le Miserables', 3, 'PROSE', 3, 2, true);
insert into books ( book_title, book_author, book_genre, copies, available, enabled) values ( 'Trīs musketieri', 4, 'NOVEL', 3, 3, true);
insert into books ( book_title, book_author, book_genre, copies, available, enabled) values ( 'Baltais Ilknis', 5, 'NOVEL', 4, 4, true);

insert into reservations (reservation_date, book, user, status)values ( '2017-12-17', 1, 3, 'RETURNED');
insert into reservations (reservation_date, book, user, status)values ( '2018-06-22', 4, 2, 'RETURNED');
insert into reservations (reservation_date, book, user, status)values ( '2019-01-11', 4, 3, 'HANDOUT');
insert into reservations (reservation_date, book, user, status)values ( '2019-08-07', 2, 2, 'HANDOUT');
insert into reservations (reservation_date, book, user, status)values ( '2019-09-05', 1, 2, 'QUEUE');
