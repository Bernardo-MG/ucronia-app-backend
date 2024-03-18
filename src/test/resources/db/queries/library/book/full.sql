INSERT INTO library.books (id, index, title, isbn, language) VALUES
   (1, 1, 'Title', 'isbn', 'english');

INSERT INTO library.book_types (id, name) VALUES
   (1, 'Book type');

INSERT INTO library.authors (id, name) VALUES
   (1, 'Author');

INSERT INTO library.game_systems (id, name) VALUES
   (1, 'Game system');

INSERT INTO library.book_book_types (book_id, book_type_id) VALUES
   (1, 1);

INSERT INTO library.book_game_systems (book_id, game_system_id) VALUES
   (1, 1);

INSERT INTO library.book_authors (book_id, author_id) VALUES
   (1, 1);