INSERT INTO association.persons (id, number, first_name, last_name, phone, identifier) VALUES
   (1, 10, 'Person 1', 'Last name 1', '12345', '6789');

INSERT INTO association.memberships (person, active) VALUES
   (1, true);