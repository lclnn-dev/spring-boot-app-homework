ALTER TABLE users
    ADD COLUMN IF NOT EXISTS pass_id BIGINT;

ALTER TABLE users
    ADD CONSTRAINT fk_users_work_pass
        FOREIGN KEY (pass_id) REFERENCES workpasses(id);
