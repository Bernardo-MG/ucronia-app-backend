INSERT INTO directory.contacts (id, number, first_name, last_name, identifier, birth_date, member, active) VALUES
   (1, 10, 'Contact 1', 'Last name 1', '6789', '1990-01-01 0:0:0', true, true);
INSERT INTO directory.members (contact_id, active) VALUES
   (1, false);