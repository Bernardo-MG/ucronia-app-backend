INSERT INTO directory.profiles (id, number, first_name, last_name, identifier, birth_date, address, comments, types) VALUES
   (1, 10, 'Profile 1', 'Last name 1', '6789', '1990-01-01 0:0:0', 'Address', 'Comments', '["member"]');
INSERT INTO directory.members (id, fee_type_id, active, renew_membership) VALUES
   (1, 1, true, true);