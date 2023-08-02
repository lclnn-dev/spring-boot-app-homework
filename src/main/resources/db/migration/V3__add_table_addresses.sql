CREATE TABLE IF NOT EXISTS addresses
(
    id                 bigserial PRIMARY KEY,
    address_has_active boolean,
    city               varchar(255),
    country            varchar(255),
    street             varchar(255),
    employee_id        integer,

    CONSTRAINT fk_addresses_on_USERS FOREIGN KEY (employee_id) REFERENCES users (id)
);