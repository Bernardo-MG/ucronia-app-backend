INSERT INTO directory.profiles (id, number, first_name, last_name, identifier, birth_date, address, comments, types) VALUES
   (2, 20, 'Profile 2', 'Last name 2', '67890', '1990-01-01 0:0:0', 'Address', 'Comments', '["member"]');
INSERT INTO directory.members (id, fee_type_id, active, renew_membership) VALUES
   (2, 1, true, true);