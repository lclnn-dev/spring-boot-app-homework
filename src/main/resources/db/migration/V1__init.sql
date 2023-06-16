CREATE TABLE if not exists USERS
(
    id         SERIAL PRIMARY KEY,
    country    VARCHAR(255),
    email      VARCHAR(255),
    gender     VARCHAR(255),
    is_deleted BOOLEAN,
    name       VARCHAR(255)
);

create table if not exists addresses
(
    id                 bigserial PRIMARY KEY,
    address_has_active boolean,
    city               varchar(255),
    country            varchar(255),
    street             varchar(255),
    employee_id        integer,

    CONSTRAINT fk_addresses_on_USERS FOREIGN KEY (employee_id) REFERENCES users (id)
);
