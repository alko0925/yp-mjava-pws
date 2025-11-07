-- Таблица с пользователями
create table if not exists users(
  id bigserial primary key,
  first_name varchar(256) not null,
  last_name varchar(256) not null,
  age integer not null,
  active boolean not null);

insert into users(first_name, last_name, age, active) values ('Иван', 'Иванов', 30, true);
insert into users(first_name, last_name, age, active) values ('Пётр', 'Петров', 25, false);
insert into users(first_name, last_name, age, active) values ('Мария', 'Сидорова', 28, true);

CREATE SEQUENCE IF NOT EXISTS seq_post_id AS INTEGER START 1;
CREATE SEQUENCE IF NOT EXISTS seq_comment_id AS INTEGER START 1;

CREATE TABLE IF NOT EXISTS posts (
    id INTEGER PRIMARY KEY,
    title VARCHAR(400) NOT NULL,
    text TEXT NOT NULL,
    tags VARCHAR(400) NOT NULL,
    likesCount INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS comments (
    id INTEGER PRIMARY KEY,
	post_id INTEGER REFERENCES posts ON DELETE CASCADE,
    text VARCHAR(400) NOT NULL
);