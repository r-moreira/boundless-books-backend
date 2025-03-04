ALTER TABLE books_metadata
    ADD CONSTRAINT uc_books_metadata_title UNIQUE (title);