DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals(dateTime, description, calories, user_id) VALUES
  (to_timestamp('21-06-2016 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'Завтрак', 300, 100000);
INSERT INTO meals(dateTime, description, calories, user_id) VALUES
  (to_timestamp('22-06-2016 21:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'Ужин', 600, 100000);