INSERT INTO directory.contacts (id, number, first_name, last_name, identifier, birth_date, comments) VALUES
   (1, 10, 'Contact 1', 'Last name 1', '6789', '1990-01-01 0:0:0', 'Comments'),
   (2, 20, 'Contact 2', 'Last name 2', '12346', '1990-01-01 0:0:0', 'Comments'),
   (3, 30, 'Contact 3', 'Last name 3', '12347', '1990-01-01 0:0:0', 'Comments'),
   (4, 40, 'Contact 4', 'Last name 4', '12348', '1990-01-01 0:0:0', 'Comments'),
   (5, 50, 'Contact 5', 'Last name 5', '12349', '1990-01-01 0:0:0', 'Comments');
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