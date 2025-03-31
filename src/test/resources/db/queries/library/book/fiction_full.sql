INSERT INTO inventory.authors (id, number, name) VALUES
   (1, 1, 'Author');

INSERT INTO inventory.publishers (id, number, name) VALUES
   (1, 1, 'Publisher');

INSERT INTO inventory.books (id, number, supertitle, title, subtitle, isbn, publish_date, donation_date, language, data_type) VALUES
   (1, 1, 'Supertitle', 'Title', 'Subtitle', '1-56619-909-3', '1990-01-01', '1995-01-02', 'en', 'fiction');

INSERT INTO inventory.book_authors (book_id, author_id) VALUES
   (1, 1);

INSERT INTO inventory.book_publishers (book_id, publisher_id) VALUES
   (1, 1);

INSERT INTO inventory.book_donors (book_id, donor_id) VALUES
   (1, 1);