INSERT INTO directory.profiles (id, number, first_name, last_name, identifier, birth_date, address, comments) VALUES
   (1, 10, 'Profile 1', 'Last name 1', '6789', '1990-01-01 0:0:0', 'Address', 'Comments');

INSERT INTO directory.contact_channels (id, contact_method_id, profile_id, detail) VALUES
   (1, 1, 1, 'email@somewhere.com');