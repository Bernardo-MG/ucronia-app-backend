INSERT INTO directory.profiles (id, number, first_name, last_name, identifier, birth_date, address, comments, types) VALUES
   (1, 10, 'Name 1', 'Last name 1', '6789', '1990-01-01 0:0:0', 'Address', 'Comments', '["sponsor"]');
INSERT INTO directory.sponsors (id) VALUES
   (1);
INSERT INTO directory.sponsor_years (sponsor_id, year) VALUES
   (1, 2025);
