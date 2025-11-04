INSERT INTO directory.contacts (id, number, first_name, last_name, identifier, birth_date, member, active) VALUES
   (1, 10, 'Contact 1', 'Last name 1', '6789', '1990-01-01 0:0:0', true, true),
   (2, 20, 'Contact 2', 'Last name 2', '12346', '1990-01-01 0:0:0', true, true),
   (3, 30, 'Contact 3', 'Last name 3', '12347', '1990-01-01 0:0:0', true, true),
   (4, 40, 'Contact 4', 'Last name 4', '12348', '1990-01-01 0:0:0', true, true),
   (5, 50, 'Contact 5', 'Last name 5', '12349', '1990-01-01 0:0:0', true, true);
INSERT INTO directory.members (contact_id, active) VALUES
   (1, true),
   (2, true),
   (3, true),
   (4, true),
   (5, true);