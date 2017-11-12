CREATE DATABASE phones_magazine ENCODING 'UTF-8';

--Создаем таблицу ролей для пользователей системы.
CREATE TABLE IF NOT EXISTS roles (
  id   SERIAL PRIMARY KEY,
  role VARCHAR(5) NOT NULL
);
--Заполняем роли.
INSERT INTO roles (id, role) VALUES (DEFAULT, 'admin');
INSERT INTO roles (id, role) VALUES (DEFAULT, 'user');

--Создаем таблицу пользователей.
CREATE TABLE IF NOT EXISTS users (
  id       SERIAL PRIMARY KEY,
  login    VARCHAR(10) UNIQUE NOT NULL,
  password VARCHAR(10) UNIQUE NOT NULL,
  role     INTEGER     NOT NULL,
  FOREIGN KEY (role) REFERENCES roles (id)
);

--Выгрузить пользователя с ролью.
SELECT u.id, u.login, u.password, r.id AS rol_id, r.role FROM users AS u LEFT JOIN roles AS r ON u.role = r.id WHERE u.login = (?);
--Удалить пользователя
DELETE FROM users WHERE id = (?) AND login = (?) AND password = (?) RETURNING id;
--Обновить пользователя
UPDATE users SET password = (?) WHERE id = (?) RETURNING id;

--Вставляем новых пользователей в таблицу.
INSERT INTO users (id, login, password, role) VALUES (DEFAULT, 'admin', '123', 1);
INSERT INTO users (id, login, password, role) VALUES (DEFAULT, 'user', '1234', 2);

--Создаем таблицу моделей.
CREATE TABLE IF NOT EXISTS phone_models (
  id   SERIAL PRIMARY KEY,
  name VARCHAR(15) UNIQUE NOT NULL
);

--Вставляем модели в таблицу.
INSERT INTO phone_models (id, name) VALUES (DEFAULT, 'samsung')RETURNING id;
INSERT INTO phone_models (id, name) VALUES (DEFAULT, 'iphone')RETURNING id;
INSERT INTO phone_models (id, name) VALUES (DEFAULT, 'xaomi')RETURNING id;
INSERT INTO phone_models (id, name) VALUES (DEFAULT, 'digma')RETURNING id;


--Создаем таблицу для записей информации о продажах.
CREATE TABLE IF NOT EXISTS phones_sale (
  id       SERIAL PRIMARY KEY,
  model_id INTEGER   NOT NULL,
  price    DECIMAL   NOT NULL,
  date     TIMESTAMP NOT NULL,
  user_id  INTEGER   NOT NULL,
  FOREIGN KEY (model_id) REFERENCES phone_models (id),
  FOREIGN KEY (user_id) REFERENCES users (id)
);

--Вставляем данные о продажах.
INSERT INTO phones_sale (id, model_id, price, date, user_id) VALUES (DEFAULT, 1, 58000, now(), 2);
INSERT INTO phones_sale (id, model_id, price, date, user_id) VALUES (DEFAULT, 2, 90000, now(), 2);
INSERT INTO phones_sale (id, model_id, price, date, user_id) VALUES (DEFAULT, 3, 30000, now(), 2);
INSERT INTO phones_sale (id, model_id, price, date, user_id) VALUES (DEFAULT, 1, 64000, now(), 1);
INSERT INTO phones_sale (id, model_id, price, date, user_id) VALUES (DEFAULT, 3, 35000, now(), 1);

--Выборка общей суммы продаж по промежутку времени.
SELECT sum(p.price)
FROM phones_sale AS p
WHERE p.date >= '2017-10-04 00:16:22.123102' AND
      p.date <= '2017-12-30 23:21:31.59098';

--Получение суммы выручки по модели за промежуток времени.
SELECT sum(p.price)
FROM phones_sale AS p
  LEFT JOIN phone_models AS m ON p.model_id = m.id
WHERE m.name = 'samsung'
      AND p.date >= '2017-10-02 23:21:31.59098'
      AND p.date <= '2017-12-05 23:21:31.59098';

--Получить модели выручка которых меньше чем 100000 за промежуток времени
SELECT
  m.name,
  sum(p.price) AS cost
FROM (
       SELECT *
       FROM phones_sale
       WHERE date BETWEEN '2017-11-02 23:21:31.59098' AND '2017-12-05 23:21:31.59098'
     ) AS p
  INNER JOIN phone_models AS m
    ON p.model_id = m.id
GROUP BY m.name
HAVING sum(p.price) < 10000000
ORDER BY cost DESC;


--Получить статистику продаж по убыванию среди определенных моделей.
SELECT
  m.name,
  sum(p.price) AS cost
FROM (
       SELECT *
       FROM phones_sale
       WHERE date BETWEEN '2017-11-02 23:21:31.59098' AND '2017-12-05 23:21:31.59098'
     ) AS p
  INNER JOIN phone_models AS m
    ON p.model_id = m.id AND (m.name = 'samsung' OR m.name = 'iphone' OR m.name = 'xaomi')
GROUP BY m.name
ORDER BY cost DESC;


