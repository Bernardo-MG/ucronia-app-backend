INSERT INTO inventory.keys (id, number, available, description) VALUES
   (1, 10, true, 'Main entrance key'),
   (10, 20, true, 'Main entrance key');
INSERT INTO directory.profiles (id, number, first_name, last_name, identifier, birth_date, address, comments, types) VALUES
   (1, 10, 'Name 1', 'Last name 1', '6789', '1990-01-01 0:0:0', 'Address', 'Comments', '["member"]');
INSERT INTO directory.members (id, member_number, fee_type_id, active, renew_membership) VALUES
   (1, 15, 1, true, true);
INSERT INTO directory.key_assignments (key_id, profile_id) VALUES
   (1, 1),
   (10, 1);