INSERT INTO persons (id, number, name, surname, phone, identifier) VALUES
   (1, 10, 'Member 1', 'Surname 1', '12345', '6789'),
   (2, 20, 'Member 2', 'Surname 2', '12346', '6790'),
   (3, 30, 'Member 3', 'Surname 3', '12347', '6791'),
   (4, 40, 'Member 4', 'Surname 4', '12348', '6792'),
   (5, 50, 'Member 5', 'Surname 5', '12349', '6793');

INSERT INTO members (id, person) VALUES
   (1, 1),
   (2, 2),
   (3, 3),
   (4, 4),
   (5, 5);