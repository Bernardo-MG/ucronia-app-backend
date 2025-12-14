INSERT INTO directory.contacts (id, number, first_name, last_name, identifier, birth_date, comments) VALUES
   (1, 10, 'Contact 1', 'Last name 1', '6789', '1990-01-01 0:0:0', 'Comments');

INSERT INTO directory.contact_channels (id, contact_method_id, contact_id, detail) VALUES
   (1, 1, 1, 'email@somewhere.com');