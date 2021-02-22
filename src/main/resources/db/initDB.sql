DROP TABLE IF EXISTS user_roles CASCADE ;
DROP TABLE IF EXISTS users CASCADE ;
DROP TABLE IF EXISTS meals CASCADE ;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                           NOT NULL,
    email            VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    enabled          BOOL                DEFAULT TRUE  NOT NULL,
    calories_per_day INTEGER             DEFAULT 2000  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

INSERT INTO users (name, email, password, registered, enabled, calories_per_day) VALUES ('Alex', 'aa@aa.aa', 'aaa', '2020-02-22', true, 1000);
INSERT INTO users (name, email, password, registered, enabled, calories_per_day) VALUES ('Pupa', 'bbb@bb.bb', 'bbb', '2020-02-22', true, 1000);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

INSERT INTO user_roles (user_id, role) VALUES (100000, 'user');
INSERT INTO user_roles (user_id, role) VALUES (100001, 'admin');

CREATE TABLE meals
(
    id         INTEGER not null PRIMARY KEY DEFAULT nextval('global_seq'),
    dateTime        TIMESTAMP not null,
    description     VARCHAR not null,
    calories        INTEGER not null,
    userId          INTEGER not null,
    CONSTRAINT meals_idx UNIQUE (id, dateTime),
    FOREIGN KEY (userId) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX meals_date_time_idx ON meals (dateTime, userid);
CREATE INDEX meals_descr_idx ON meals (description);

INSERT INTO meals (dateTime, description, calories, userId) VALUES ('2021-01-30 10:00', 'завтрак', 500, 100000);
INSERT INTO meals (dateTime, description, calories, userId) VALUES ('2021-01-30 13:00', 'обед', 1000, 100000);
INSERT INTO meals (dateTime, description, calories, userId) VALUES ('2021-01-30 20:00', 'ужин', 500, 100000);
INSERT INTO meals (dateTime, description, calories, userId) VALUES ('2021-01-31 00:00', 'пограничное значение', 100, 100000);
INSERT INTO meals (dateTime, description, calories, userId) VALUES ('2021-01-31 10:00', 'завтрак', 1000, 100000);
INSERT INTO meals (dateTime, description, calories, userId) VALUES ('2021-01-31 13:00', 'обед', 500, 100000);
INSERT INTO meals (dateTime, description, calories, userId) VALUES ('2021-01-31 20:00', 'ужин', 410, 100000);

INSERT INTO meals (dateTime, description, calories, userId) VALUES ('2021-01-31 20:00', 'ужин', 410, 100001);
