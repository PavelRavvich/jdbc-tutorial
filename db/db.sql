CREATE DATABASE phones_magazine ENCODING 'UTF-8';

CREATE TABLE IF NOT EXISTS roles (
  id   SERIAL PRIMARY KEY,
  role VARCHAR(5) NOT NULL
);

INSERT INTO roles (id, role) VALUES (DEFAULT, 'admin');
INSERT INTO roles (id, role) VALUES (DEFAULT, 'user');


CREATE TABLE IF NOT EXISTS users (
  id       SERIAL PRIMARY KEY,
  login    VARCHAR(10) UNIQUE NOT NULL,
  password VARCHAR(10) UNIQUE NOT NULL,
  role     INTEGER     NOT NULL,
  FOREIGN KEY (role) REFERENCES roles (id)
);

--Создаем пользователей.
INSERT INTO users (id, login, password, role) VALUES (DEFAULT, 'Pavel', '123', 1);
INSERT INTO users (id, login, password, role)VALUES (DEFAULT, 'Egor', '1234', 2);


--ДЛЯ САМОСТОЯТЕЛЬНОГО ИЗУЧЕНИЯ--
--Выгрузить пользователя с ролью.
SELECT u.id, u.login, u.password, r.id AS rol_id, r.role FROM users AS u LEFT JOIN roles AS r ON u.role = r.id WHERE u.login = (?);
--Удалить пользователя.
DELETE FROM users WHERE id = (?) AND login = (?) AND password = (?) RETURNING id;
--Обновить пользователя.
UPDATE users SET password = (?) WHERE id = (?) RETURNING id;


