INSERT INTO directory.profiles (id, number, first_name, last_name, identifier, birth_date, comments, types) VALUES
   (1, 10, 'Profile 1', 'Last name 1', '6789', '1990-01-01 0:0:0', 'Comments', '["sponsor"]');
INSERT INTO directory.sponsors (id) VALUES
   (1);
INSERT INTO directory.sponsor_years (sponsor_id, year) VALUES
   (1, 2025);

INSERT INTO directory.contact_methods (id, number, name) VALUES
   (1, 10, 'email');
INSERT INTO directory.contact_channels (id, contact_method_id, profile_id, detail) VALUES
   (1, 1, 1, 'email@somewhere.com');