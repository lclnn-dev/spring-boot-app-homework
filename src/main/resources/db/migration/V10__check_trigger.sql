DROP TRIGGER IF EXISTS trigger_user_country_null ON users;

CREATE OR REPLACE FUNCTION check_employees_country()
    RETURNS TRIGGER
AS
$$
BEGIN
    IF NEW.country IS NULL THEN
        RAISE EXCEPTION 'Country cannot be null';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_user_country_null
    BEFORE INSERT OR UPDATE
    ON users
    FOR EACH ROW
EXECUTE FUNCTION check_employees_country();

--
DROP TRIGGER IF EXISTS trigger_workplace_name_null ON workplaces;

CREATE OR REPLACE FUNCTION check_workplaces_name()
    RETURNS TRIGGER
AS
$$
BEGIN
    IF NEW.name IS NULL THEN
        RAISE EXCEPTION 'Name cannot be null';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_workplace_name_null
    BEFORE INSERT OR UPDATE
    ON workplaces
    FOR EACH ROW
EXECUTE FUNCTION check_workplaces_name();

--

DROP TRIGGER IF EXISTS trigger_workplace_stamp ON workplaces;
DROP FUNCTION IF EXISTS workplace_stamp() CASCADE;

ALTER TABLE workplaces
    ADD COLUMN IF NOT EXISTS last_date timestamp,
    ADD COLUMN IF NOT EXISTS last_user text;

CREATE FUNCTION workplace_stamp() RETURNS trigger AS
$workplace_stamp$
BEGIN
    NEW.last_date := current_timestamp;
    NEW.last_user := current_user;
    RETURN NEW;
END;
$workplace_stamp$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_workplace_stamp
    BEFORE INSERT OR UPDATE
    ON workplaces
    FOR EACH ROW
EXECUTE PROCEDURE workplace_stamp();

--

DROP FUNCTION IF EXISTS add_to_workplace_history() CASCADE;
DROP TRIGGER IF EXISTS trigger_workplace_history ON workplaces;

CREATE TABLE IF NOT EXISTS workplace_history
(
    operation    char(1)   NOT NULL,
    workplace_id bigint    NOT NULL,
    stamp        timestamp NOT NULL
);

CREATE OR REPLACE FUNCTION add_to_workplace_history() RETURNS TRIGGER AS
$$
BEGIN
    IF (TG_OP = 'DELETE') THEN
        INSERT INTO workplace_history (operation, workplace_id, stamp)
        VALUES ('D', OLD.id, now());
        RETURN OLD;
    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO workplace_history (operation, workplace_id, stamp)
        VALUES ('U', NEW.id, now());
        RETURN NEW;
    ELSIF (TG_OP = 'INSERT') THEN
        INSERT INTO workplace_history (operation, workplace_id, stamp)
        VALUES ('I', NEW.id, now());
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trigger_workplace_history
    AFTER INSERT OR UPDATE OR DELETE
    ON workplaces
    FOR EACH ROW
EXECUTE PROCEDURE add_to_workplace_history();