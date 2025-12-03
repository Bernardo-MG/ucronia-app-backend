INSERT INTO directory.contacts (id, number, first_name, last_name, identifier, birth_date) VALUES
   (1, 10, 'Contact é', 'Last name 1', '6789', '1990-01-01 0:0:0'),
   (2, 20, 'Contact a', 'Last name 2', '12346', '1990-01-01 0:0:0'),
   (3, 30, 'Contact i', 'Last name 3', '12347', '1990-01-01 0:0:0'),
   (4, 40, 'Contact o', 'Last name 4', '12348', '1990-01-01 0:0:0'),
   (5, 50, 'Contact u', 'Last name 5', '12349', '1990-01-01 0:0:0');
INSERT INTO directory.members (contact_id, active, renew_membership) VALUES
   (1, false, true),
   (2, false, true),
   (3, false, true),
   (4, false, true),
   (5, false, true);