INSERT INTO directory.contacts (id, number, first_name, last_name, identifier, birth_date, comments, types) VALUES
   (1, 10, 'Contact é', 'Last name 1', '6789', '1990-01-01 0:0:0', 'Comments', '["member"]'),
   (2, 20, 'Contact a', 'Last name 2', '12346', '1990-01-01 0:0:0', 'Comments', '["member"]'),
   (3, 30, 'Contact i', 'Last name 3', '12347', '1990-01-01 0:0:0', 'Comments', '["member"]'),
   (4, 40, 'Contact o', 'Last name 4', '12348', '1990-01-01 0:0:0', 'Comments', '["member"]'),
   (5, 50, 'Contact u', 'Last name 5', '12349', '1990-01-01 0:0:0', 'Comments', '["member"]');
INSERT INTO directory.members (id, active, renew_membership) VALUES
   (1, true, true),
   (2, true, true),
   (3, true, true),
   (4, true, true),
   (5, true, true);