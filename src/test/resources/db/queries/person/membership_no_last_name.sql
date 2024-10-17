INSERT INTO association.persons (id, number, first_name, last_name, phone, identifier) VALUES
   (1, 10, 'Person 1', '', '12345', '6789');

INSERT INTO association.members (person, active) VALUES
   (1, false);