INSERT INTO inventory.books (id, number, title, isbn, language) VALUES
   (1, 1, 'Title', '1-56619-909-3', 'en');

INSERT INTO inventory.authors (id, number, name) VALUES
   (1, 1, 'Author');

INSERT INTO inventory.publishers (id, name) VALUES
   (1, 'Publisher');

INSERT INTO inventory.book_types (id, name) VALUES
   (1, 'Book type');

INSERT INTO inventory.game_systems (id, name) VALUES
   (1, 'Game system');

INSERT INTO inventory.book_authors (book_id, author_id) VALUES
   (1, 1);

INSERT INTO inventory.book_publishers (book_id, publisher_id) VALUES
   (1, 1);

INSERT INTO inventory.book_book_types (book_id, book_type_id) VALUES
   (1, 1);

INSERT INTO inventory.book_game_systems (book_id, game_system_id) VALUES
   (1, 1);

INSERT INTO inventory.book_donors (book_id, donor_id) VALUES
   (1, 1);