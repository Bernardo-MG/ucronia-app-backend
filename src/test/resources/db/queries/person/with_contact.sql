INSERT INTO association.persons (id, number, first_name, last_name, identifier, birth_date, member, active) VALUES
   (1, 10, 'Person 1', 'Last name 1', '6789', '1990-01-01 0:0:0', true, true);

INSERT INTO association.person_contact_methods (contact_method_id, person_id, contact) VALUES
   (1, 1, 'email@somewhere.com');