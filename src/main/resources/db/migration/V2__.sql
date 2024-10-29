CREATE TABLE google_users
(
    id    VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    name  VARCHAR(255),
    CONSTRAINT pk_google_users PRIMARY KEY (id)
);