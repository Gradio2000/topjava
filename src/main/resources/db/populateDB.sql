DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);



INSERT INTO meals (dateTime, description, calories, userId) VALUES ('2021-01-30 10:00', 'завтрак', 500, 100000);
INSERT INTO meals (dateTime, description, calories, userId) VALUES ('2021-01-30 13:00', 'обед', 1000, 100000);
INSERT INTO meals (dateTime, description, calories, userId) VALUES ('2021-01-30 20:00', 'ужин', 500, 100000);
INSERT INTO meals (dateTime, description, calories, userId) VALUES ('2021-01-31 00:00', 'пограничное значение', 100, 100000);
INSERT INTO meals (dateTime, description, calories, userId) VALUES ('2021-01-31 10:00', 'завтрак', 1000, 100000);
INSERT INTO meals (dateTime, description, calories, userId) VALUES ('2021-01-31 13:00', 'обед', 500, 100000);
INSERT INTO meals (dateTime, description, calories, userId) VALUES ('2021-01-31 20:00', 'ужин', 410, 100000);

INSERT INTO meals (dateTime, description, calories, userId) VALUES ('2021-01-31 20:00', 'ужин', 410, 100001);
