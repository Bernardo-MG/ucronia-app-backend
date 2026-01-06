INSERT INTO directory.profiles (id, number, first_name, last_name, identifier, birth_date, comments, types) VALUES
   (1, 10, 'Profile 1', 'Last name 1', '10', '1990-01-01 0:0:0', 'Comments', '["member"]'),
   (2, 20, 'Profile 2', 'Last name 2', '20', '1990-01-01 0:0:0', 'Comments', '["member"]'),
   (3, 30, 'Profile 3', 'Last name 3', '30', '1990-01-01 0:0:0', 'Comments', '["member"]'),
   (4, 40, 'Profile 4', 'Last name 4', '40', '1990-01-01 0:0:0', 'Comments', '["member"]'),
   (5, 50, 'Profile 5', 'Last name 5', '50', '1990-01-01 0:0:0', 'Comments', '["member"]');
INSERT INTO directory.members (id, fee_type_id, active, renew_membership) VALUES
   (1, 1, false, true),
   (2, 1, false, true),
   (3, 1, false, true),
   (4, 1, false, true),
   (5, 1, false, true);

INSERT INTO directory.contact_methods (id, number, name) VALUES
   (1, 10, 'email');
INSERT INTO directory.contact_channels (id, contact_method_id, profile_id, detail) VALUES
   (1, 1, 1, 'email@somewhere.com'),
   (2, 1, 2, 'email@somewhere.com'),
   (3, 1, 3, 'email@somewhere.com'),
   (4, 1, 4, 'email@somewhere.com'),
   (5, 1, 5, 'email@somewhere.com');