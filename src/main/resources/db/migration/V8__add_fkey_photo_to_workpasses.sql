ALTER TABLE workpasses
    ADD COLUMN IF NOT EXISTS photo_id BIGINT;

ALTER TABLE workpasses
    ADD CONSTRAINT fk_workpass_photo
        FOREIGN KEY (photo_id) REFERENCES photos_pass (id);
