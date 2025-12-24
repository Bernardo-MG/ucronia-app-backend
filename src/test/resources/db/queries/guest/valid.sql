INSERT INTO directory.contacts (id, number, first_name, last_name, identifier, birth_date, comments) VALUES
   (1, 10, 'Contact 1', 'Last name 1', '6789', '1990-01-01 0:0:0', 'Comments');
INSERT INTO directory.guests (id) VALUES
   (1);
INSERT INTO directory.guest_games (guest_id, date) VALUES
   (1, '2025-01-01 0:0:0');

INSERT INTO directory.contact_methods (id, number, name) VALUES
   (1, 10, 'email');
INSERT INTO directory.contact_channels (id, contact_method_id, contact_id, detail) VALUES
   (1, 1, 1, 'email@somewhere.com');