INSERT INTO association.persons (id, number, first_name, last_name, phone, identifier) VALUES
   (1, 10, 'Person é', 'Last name 1', '12345', '6789'),
   (2, 20, 'Person a', 'Last name 2', '12346', '6790'),
   (3, 30, 'Person i', 'Last name 3', '12347', '6791'),
   (4, 40, 'Person o', 'Last name 4', '12348', '6792'),
   (5, 50, 'Person u', 'Last name 5', '12349', '6793');

INSERT INTO association.memberships (id, person, active) VALUES
   (1, 1, false),
   (2, 2, false),
   (3, 3, false),
   (4, 4, false),
   (5, 5, false);