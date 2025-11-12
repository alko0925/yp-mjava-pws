CREATE TABLE users(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    age        INT         NOT NULL,
    active     BOOLEAN     NOT NULL
);

INSERT INTO users(first_name, last_name, age, active)
VALUES ('Иван', 'Иванов', 30, true),
       ('Пётр', 'Петров', 25, false),
       ('Мария', 'Сидорова', 28, true);