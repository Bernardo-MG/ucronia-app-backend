INSERT INTO association.transactions (id, index, description, amount, date) VALUES
   (1, 1, 'Transaction', 2, '2020-02-01'),
   (2, 2, 'Transaction', 2, '2020-02-01'),
   (3, 3, 'Transaction', 2, '2020-02-01'),
   (4, 4, 'Transaction', 2, '2020-02-01'),
   (5, 5, 'Transaction', 2, '2020-02-01'),
   (6, 6, 'Transaction', 2, '2020-02-01'),
   (7, 7, 'Transaction', 2, '2020-02-01'),
   (8, 8, 'Transaction', 2, '2020-02-01'),
   (9, 9, 'Transaction', 2, '2020-02-01'),
   (10, 10, 'Transaction', 2, '2020-02-01'),
   (11, 11, 'Transaction', 2, '2020-02-01'),
   (12, 12, 'Transaction', 2, '2020-02-01');

INSERT INTO association.fees (id, member_id, date, paid, fee_type_id, transaction_id) VALUES
   (1, 1, '2020-01-01', true, 1, 1),
   (2, 1, '2020-02-01', true, 1, 2),
   (3, 1, '2020-03-01', true, 1, 3),
   (4, 1, '2020-04-01', true, 1, 4),
   (5, 1, '2020-05-01', true, 1, 5),
   (6, 1, '2020-06-01', true, 1, 6),
   (7, 1, '2020-07-01', true, 1, 7),
   (8, 1, '2020-08-01', true, 1, 8),
   (9, 1, '2020-09-01', true, 1, 9),
   (10, 1, '2020-10-01', true, 1, 10),
   (11, 1, '2020-11-01', true, 1, 11),
   (12, 1, '2020-12-01', true, 1, 12);
