INSERT INTO google_users (id, email, name, created_at, updated_at)
SELECT 'john.doe', 'john.doe@gmail.com', 'John Doe', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM google_users WHERE id = 'john.doe');

INSERT INTO google_users (id, email, name, created_at, updated_at)
SELECT 'jane.smith', 'jane.smith@yahoo.com', 'Jane Smith', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM google_users WHERE id = 'jane.smith');

INSERT INTO google_users (id, email, name, created_at, updated_at)
SELECT 'michael.brown', 'michael.brown@outlook.com', 'Michael Brown', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM google_users WHERE id = 'michael.brown');

INSERT INTO google_users (id, email, name, created_at, updated_at)
SELECT 'emily.johnson', 'emily.johnson@icloud.com', 'Emily Johnson', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM google_users WHERE id = 'emily.johnson');

INSERT INTO google_users (id, email, name, created_at, updated_at)
SELECT 'david.wilson', 'david.wilson@gmail.com', 'David Wilson', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM google_users WHERE id = 'david.wilson');


INSERT INTO user_profiles (google_user_id, created_at, updated_at)
SELECT 'john.doe', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM user_profiles WHERE google_user_id = 'john.doe');

INSERT INTO user_profiles (google_user_id, created_at, updated_at)
SELECT 'jane.smith', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM user_profiles WHERE google_user_id = 'jane.smith');

INSERT INTO user_profiles (google_user_id, created_at, updated_at)
SELECT 'michael.brown', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM user_profiles WHERE google_user_id = 'michael.brown');

INSERT INTO user_profiles (google_user_id, created_at, updated_at)
SELECT 'emily.johnson', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM user_profiles WHERE google_user_id = 'emily.johnson');

INSERT INTO user_profiles (google_user_id, created_at, updated_at)
SELECT 'david.wilson', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM user_profiles WHERE google_user_id = 'david.wilson');


INSERT INTO user_favorite_books (user_id, book_id)
SELECT (SELECT id FROM user_profiles WHERE google_user_id = 'john.doe'), 1
WHERE NOT EXISTS (SELECT 1 FROM user_favorite_books WHERE user_id = (SELECT id FROM user_profiles WHERE google_user_id = 'john.doe') AND book_id = 1);

INSERT INTO user_favorite_books (user_id, book_id)
SELECT (SELECT id FROM user_profiles WHERE google_user_id = 'john.doe'), 2
WHERE NOT EXISTS (SELECT 2 FROM user_favorite_books WHERE user_id = (SELECT id FROM user_profiles WHERE google_user_id = 'john.doe') AND book_id = 2);

INSERT INTO user_shelf_books (user_id, book_id)
SELECT (SELECT id FROM user_profiles WHERE google_user_id = 'john.doe'), 3
WHERE NOT EXISTS (SELECT 3 FROM user_shelf_books WHERE user_id = (SELECT id FROM user_profiles WHERE google_user_id = 'john.doe') AND book_id = 3);


INSERT INTO user_favorite_books (user_id, book_id)
SELECT (SELECT id FROM user_profiles WHERE google_user_id = 'jane.smith'), 2
WHERE NOT EXISTS (SELECT 1 FROM user_favorite_books WHERE user_id = (SELECT id FROM user_profiles WHERE google_user_id = 'jane.smith') AND book_id = 2);


INSERT INTO user_shelf_books (user_id, book_id)
SELECT (SELECT id FROM user_profiles WHERE google_user_id = 'michael.brown'), 3
WHERE NOT EXISTS (SELECT 1 FROM user_shelf_books WHERE user_id = (SELECT id FROM user_profiles WHERE google_user_id = 'michael.brown') AND book_id = 3);


INSERT INTO user_shelf_books (user_id, book_id)
SELECT (SELECT id FROM user_profiles WHERE google_user_id = 'emily.johnson'), 4
WHERE NOT EXISTS (SELECT 1 FROM user_shelf_books WHERE user_id = (SELECT id FROM user_profiles WHERE google_user_id = 'emily.johnson') AND book_id = 4);
