-- liquibase formatted sql

-- changeset renato:create_tables_notification_user_schedule
CREATE TABLE notification_status
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
insert into notification_status(name) values ('Unread');
insert into notification_status(name) values ('Read');

CREATE TABLE schedule_status
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
insert into schedule_status(name) values ('Pending');
insert into schedule_status(name) values ('In progress');
insert into schedule_status(name) values ('Finished');
insert into schedule_status(name) values ('Canceled');
insert into schedule_status(name) values ('Error');

CREATE TABLE "user"
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    email      VARCHAR(100) NOT NULL UNIQUE,
    opt_out    BOOLEAN   DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notification
(
    id                     SERIAL PRIMARY KEY,
    content                VARCHAR(255) NOT NULL,
    user_id                INT          NOT NULL,
    notification_status_id INT          NOT NULL,
    created_at             TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at             TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES "user" (id),
    FOREIGN KEY (notification_status_id) REFERENCES notification_status (id)
);

CREATE TABLE schedule
(
    id                     SERIAL PRIMARY KEY,
    content                VARCHAR(255) NOT NULL,
    notification_status_id INT          NOT NULL,
    scheduled_time         TIMESTAMP    NOT NULL,
    schedule_status_id     INT          NOT NULL,
    created_at             TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at             TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (notification_status_id) REFERENCES notification_status (id),
    FOREIGN KEY (schedule_status_id) REFERENCES schedule_status (id)
);

CREATE TABLE user_schedule
(
    user_id     INT NOT NULL,
    schedule_id INT NOT NULL,
    primary key (user_id, schedule_id),
    FOREIGN KEY (user_id) REFERENCES "user" (id),
    FOREIGN KEY (schedule_id) REFERENCES schedule (id)
);
-- rollback DROP TABLE IF EXISTS user_schedule;
-- rollback DROP TABLE IF EXISTS schedule;
-- rollback DROP TABLE IF EXISTS notification;
-- rollback DROP TABLE IF EXISTS "user";
-- rollback DROP TABLE IF EXISTS schedule_status;
-- rollback DROP TABLE IF EXISTS notification_status;