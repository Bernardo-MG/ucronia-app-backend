INSERT INTO directory.profiles (id, number, first_name, last_name, identifier, birth_date, comments, types) VALUES
   (1, 10, 'Profile 1', 'Last name 1', '10', '1990-01-01 0:0:0', 'Comments', '["guest"]'),
   (2, 20, 'Profile 2', 'Last name 2', '20', '1990-01-01 0:0:0', 'Comments', '["guest"]'),
   (3, 30, 'Profile 3', 'Last name 3', '30', '1990-01-01 0:0:0', 'Comments', '["guest"]'),
   (4, 40, 'Profile 4', 'Last name 4', '40', '1990-01-01 0:0:0', 'Comments', '["guest"]'),
   (5, 50, 'Profile 5', 'Last name 5', '50', '1990-01-01 0:0:0', 'Comments', '["guest"]');
INSERT INTO directory.guests (id) VALUES
   (1),
   (2),
   (3),
   (4),
   (5);
INSERT INTO directory.guest_games (guest_id, date) VALUES
   (1, '2025-01-01 0:0:0'),
   (2, '2025-01-01 0:0:0'),
   (3, '2025-01-01 0:0:0'),
   (4, '2025-01-01 0:0:0'),
   (5, '2025-01-01 0:0:0');