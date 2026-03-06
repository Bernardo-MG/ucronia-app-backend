INSERT INTO directory.profiles (id, number, first_name, last_name, identifier, birth_date, address, comments, types) VALUES
   (1, 10, 'Profile 1', 'Last name 1', '6789', '1990-01-01 0:0:0', 'Address', 'Comments', '["guest"]');
INSERT INTO directory.guests (id) VALUES
   (1);
INSERT INTO directory.guest_games (guest_id, date) VALUES
   (1, '2025-01-01 0:0:0');

INSERT INTO directory.contact_methods (id, number, name) VALUES
   (1, 10, 'email');
INSERT INTO directory.contact_channels (id, contact_method_id, profile_id, detail) VALUES
   (1, 1, 1, 'email@somewhere.com');