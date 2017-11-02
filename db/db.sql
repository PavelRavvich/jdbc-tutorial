CREATE DATABASE phones_magazine ENCODING 'UTF-8';

--Created 1 time.
CREATE TABLE IF NOT EXISTS models (
  id   SERIAL PRIMARY KEY,
  name VARCHAR(15) UNIQUE
);

INSERT INTO models (id, name) VALUES (DEFAULT, 'samsung');
INSERT INTO models (id, name) VALUES (DEFAULT, 'iphone');
INSERT INTO models (id, name) VALUES (DEFAULT, 'xaomi');

--Created 2 time.
CREATE TABLE IF NOT EXISTS phones (
  id       SERIAL PRIMARY KEY,
  model_id INTEGER   NOT NULL,
  prise    BIGINT    NOT NULL,
  date     TIMESTAMP NOT NULL,
  FOREIGN KEY (model_id) REFERENCES models (id)
);

INSERT INTO phones (id, model_id, prise, date)
VALUES (DEFAULT, 1, 58000, now());

INSERT INTO phones (id, model_id, prise, date)
VALUES (DEFAULT, 2, 90000, now());

INSERT INTO phones (id, model_id, prise, date)
VALUES (DEFAULT, 3, 30000, now());

INSERT INTO phones (id, model_id, prise, date)
VALUES (DEFAULT, 1, 64000, now());

INSERT INTO phones (id, model_id, prise, date)
VALUES (DEFAULT, 3, 35000, now());


SELECT sum(p.prise)
FROM phones AS p
  LEFT JOIN models AS m ON m.id = p.model_id
WHERE p.date >= '2017-11-02 23:21:31.59098' AND m.name = 'samsung';