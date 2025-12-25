INSERT INTO directory.contacts (id, number, first_name, last_name, identifier, birth_date, comments, types) VALUES
   (2, 20, 'Contact 2', 'Last name 2', '67890', '1990-01-01 0:0:0', 'Comments', '["member"]');
INSERT INTO directory.members (id, active, renew_membership) VALUES
   (2, true, true);

INSERT INTO directory.contact_methods (id, number, name) VALUES
   (1, 10, 'email');
INSERT INTO directory.contact_channels (id, contact_method_id, contact_id, detail) VALUES
   (1, 1, 1, 'email@somewhere.com');