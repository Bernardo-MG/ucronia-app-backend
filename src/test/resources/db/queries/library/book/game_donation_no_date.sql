INSERT INTO inventory.authors (id, number, name) VALUES
   (1, 1, 'Author');

INSERT INTO inventory.publishers (id, number, name) VALUES
   (1, 1, 'Publisher');

INSERT INTO inventory.book_types (id, number, name) VALUES
   (1, 1, 'Book type');

INSERT INTO inventory.game_systems (id, number, name) VALUES
   (1, 1, 'Game system');

INSERT INTO inventory.books (id, number, supertitle, title, subtitle, isbn, publish_date, donation_date, language, game_system_id, book_type_id, data_type) VALUES
   (1, 1, 'Supertitle', 'Title', 'Subtitle', '1-56619-909-3', '1990-01-01', null, 'en', 1, 1, 'game');

INSERT INTO inventory.book_authors (book_id, author_id) VALUES
   (1, 1);

INSERT INTO inventory.book_publishers (book_id, publisher_id) VALUES
   (1, 1);

INSERT INTO inventory.book_donors (book_id, donor_id) VALUES
   (1, 1);