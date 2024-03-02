INSERT INTO books (id, title, isbn, language) VALUES
   (1, 'Title', 'isbn', 'english');

INSERT INTO book_types (id, name) VALUES
   (1, 'Book type');

INSERT INTO authors (id, name) VALUES
   (1, 'Author');

INSERT INTO game_systems (id, name) VALUES
   (1, 'Game system');

INSERT INTO book_book_types (book_id, book_type_id) VALUES
   (1, 1);

INSERT INTO book_game_systems (book_id, game_system_id) VALUES
   (1, 1);

INSERT INTO book_authors (book_id, author_id) VALUES
   (1, 1);