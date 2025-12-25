INSERT INTO directory.contacts (id, number, first_name, last_name, identifier, birth_date, comments, types) VALUES
   (1, 10, 'Contact é', 'Last name 1', '10', '1990-01-01 0:0:0', 'Comments', '["member"]'),
   (2, 20, 'Contact a', 'Last name 2', '20', '1990-01-01 0:0:0', 'Comments', '["member"]'),
   (3, 30, 'Contact i', 'Last name 3', '30', '1990-01-01 0:0:0', 'Comments', '["member"]'),
   (4, 40, 'Contact o', 'Last name 4', '40', '1990-01-01 0:0:0', 'Comments', '["member"]'),
   (5, 50, 'Contact u', 'Last name 5', '50', '1990-01-01 0:0:0', 'Comments', '["member"]');
INSERT INTO directory.members (id, active, renew_membership) VALUES
   (1, true, true),
   (2, true, true),
   (3, true, true),
   (4, true, true),
   (5, true, true);

INSERT INTO directory.contact_methods (id, number, name) VALUES
   (1, 10, 'email');
INSERT INTO directory.contact_channels (id, contact_method_id, contact_id, detail) VALUES
   (1, 1, 1, 'email@somewhere.com'),
   (2, 1, 2, 'email@somewhere.com'),
   (3, 1, 3, 'email@somewhere.com'),
   (4, 1, 4, 'email@somewhere.com'),
   (5, 1, 5, 'email@somewhere.com');