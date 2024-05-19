INSERT INTO association.persons (id, number, name, surname, phone, identifier) VALUES
   (1, 10, 'Person 1', 'Surname 1', '12345', '6789');

INSERT INTO association.members (id, person, active) VALUES
   (1, 1, true);