CREATE TABLE user_profiles
(
    id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user_profiles PRIMARY KEY (id)
);

ALTER TABLE google_users
    ADD user_profile_id VARCHAR(255);

ALTER TABLE google_users
    ADD CONSTRAINT uc_google_users_user_profile UNIQUE (user_profile_id);

ALTER TABLE google_users
    ADD CONSTRAINT FK_GOOGLE_USERS_ON_USER_PROFILE FOREIGN KEY (user_profile_id) REFERENCES user_profiles (id);