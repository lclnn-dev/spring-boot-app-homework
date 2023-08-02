CREATE TABLE IF NOT EXISTS users
(
    id      SERIAL PRIMARY KEY,
    country VARCHAR(255),
    email   VARCHAR(255),
    name    VARCHAR(255)
);
