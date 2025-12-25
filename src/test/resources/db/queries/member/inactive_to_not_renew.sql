INSERT INTO directory.contacts (id, number, first_name, last_name, identifier, birth_date, comments, types) VALUES
   (1, 10, 'Contact 1', 'Last name 1', '6789', '1990-01-01 0:0:0', 'Comments', '["member"]');
INSERT INTO directory.members (id, active, renew_membership) VALUES
   (1, false, false);

INSERT INTO directory.contact_methods (id, number, name) VALUES
   (1, 10, 'email');
INSERT INTO directory.contact_channels (id, contact_method_id, contact_id, detail) VALUES
   (1, 1, 1, 'email@somewhere.com');