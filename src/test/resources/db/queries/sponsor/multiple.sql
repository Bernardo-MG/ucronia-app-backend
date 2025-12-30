INSERT INTO directory.profiles (id, number, first_name, last_name, identifier, birth_date, comments, types) VALUES
   (1, 10, 'Profile 1', 'Last name 1', '10', '1990-01-01 0:0:0', 'Comments', '["sponsor"]'),
   (2, 20, 'Profile 2', 'Last name 2', '20', '1990-01-01 0:0:0', 'Comments', '["sponsor"]'),
   (3, 30, 'Profile 3', 'Last name 3', '30', '1990-01-01 0:0:0', 'Comments', '["sponsor"]'),
   (4, 40, 'Profile 4', 'Last name 4', '40', '1990-01-01 0:0:0', 'Comments', '["sponsor"]'),
   (5, 50, 'Profile 5', 'Last name 5', '50', '1990-01-01 0:0:0', 'Comments', '["sponsor"]');
INSERT INTO directory.sponsors (id) VALUES
   (1),
   (2),
   (3),
   (4),
   (5);
INSERT INTO directory.sponsor_years (sponsor_id, year) VALUES
   (1, 2025),
   (2, 2025),
   (3, 2025),
   (4, 2025),
   (5, 2025);