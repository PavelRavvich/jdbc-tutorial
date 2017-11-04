CREATE DATABASE phones_magazine ENCODING 'UTF-8';

CREATE TABLE IF NOT EXISTS roles (
  id   SERIAL PRIMARY KEY,
  role VARCHAR(5) NOT NULL
);

INSERT INTO roles (id, role) VALUES (DEFAULT, 'admin');
INSERT INTO roles (id, role) VALUES (DEFAULT, 'user');


CREATE TABLE IF NOT EXISTS users (
  id       SERIAL PRIMARY KEY,
  login    VARCHAR(10) NOT NULL,
  password VARCHAR(10) NOT NULL,
  role     INTEGER     NOT NULL,
  FOREIGN KEY (role) REFERENCES roles (id)
);

INSERT INTO users (id, login, password, role)
VALUES (DEFAULT, 'admin', '123', 1);

INSERT INTO users (id, login, password, role)
VALUES (DEFAULT, 'user', '123', 2);

--Created 1 time.
CREATE TABLE IF NOT EXISTS models (
  id   SERIAL PRIMARY KEY,
  name VARCHAR(15) UNIQUE NOT NULL
);

INSERT INTO models (id, name) VALUES (DEFAULT, 'samsung')
RETURNING id;
;
INSERT INTO models (id, name) VALUES (DEFAULT, 'iphone')
RETURNING id;
;
INSERT INTO models (id, name) VALUES (DEFAULT, 'xaomi')
RETURNING id;
;
INSERT INTO models (id, name) VALUES (DEFAULT, 'digma')
RETURNING id;

SELECT m.name
FROM models AS m;

--Created 2 time.
CREATE TABLE IF NOT EXISTS phones (
  id       SERIAL PRIMARY KEY,
  model_id INTEGER   NOT NULL,
  price    BIGINT    NOT NULL,
  date     TIMESTAMP NOT NULL,
  user_id  INTEGER   NOT NULL,
  FOREIGN KEY (model_id) REFERENCES models (id),
  FOREIGN KEY (user_id) REFERENCES users (id)
);

INSERT INTO phones (id, model_id, price, date, user_id)
VALUES (DEFAULT, 1, 58000, now(), 2);

INSERT INTO phones (id, model_id, price, date, user_id)
VALUES (DEFAULT, 2, 90000, now(), 2);

INSERT INTO phones (id, model_id, price, date, user_id)
VALUES (DEFAULT, 3, 30000, now(), 2);

INSERT INTO phones (id, model_id, price, date, user_id)
VALUES (DEFAULT, 1, 64000, now(), 1);

INSERT INTO phones (id, model_id, price, date, user_id)
VALUES (DEFAULT, 3, 35000, now(), 1);

--Выборка общей суммы продаж по промежутку времени.
SELECT sum(p.price)
FROM phones AS p
WHERE p.date >= '2017-11-04 00:16:22.123102' AND
      p.date <= '2017-12-05 23:21:31.59098';

--Получение суммы выручки за модель за промежуток времени
SELECT *
FROM models;

SELECT sum(p.price)
FROM phones AS p
  LEFT JOIN models AS m ON p.model_id = m.id
WHERE m.name = 'samsung'
      AND p.date >= '2017-11-02 23:21:31.59098'
      AND p.date <= '2017-12-05 23:21:31.59098';


--Получить модели выручка которых меньше чем 42000 за промежуток времени
select m.name, p.price
from phones p
  inner join models m on p.model_id = m.id
where p.date between '2017-11-02 23:21:31.59098' and '2017-12-05 23:21:31.59098'
group by m.id, m.name
having sum(p.price) < 42000
order by sum(p.price) desc;








