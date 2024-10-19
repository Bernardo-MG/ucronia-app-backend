INSERT INTO association.persons (id, number, first_name, last_name, phone, identifier) VALUES
   (2, 20, 'Person 2', 'Last name 2', '12345', '6789');

INSERT INTO association.memberships (person, active) VALUES
   (2, true);