INSERT INTO inventory.keys (id, number, available, description) VALUES
   (100, 110, true, 'Main entrance key');
INSERT INTO directory.profiles (id, number, first_name, last_name, identifier, birth_date, address, comments, types) VALUES
   (2, 20, 'Name 2', 'Last name 2', '67890', '1990-01-01 0:0:0', 'Address', 'Comments', '["member"]');
INSERT INTO directory.members (id, member_number, fee_type_id, active, renew_membership) VALUES
   (2, 25, 1, true, true);
INSERT INTO directory.key_assignments (key_id, profile_id) VALUES
   (100, 2);