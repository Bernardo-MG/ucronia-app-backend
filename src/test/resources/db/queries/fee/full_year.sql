INSERT INTO fees (id, member_id, date) VALUES
   (1, 1, '2020-02-01'),
   (2, 1, '2020-01-01'),
   (3, 1, '2020-03-01'),
   (4, 1, '2020-04-01'),
   (5, 1, '2020-05-01'),
   (6, 1, '2020-06-01'),
   (7, 1, '2020-07-01'),
   (8, 1, '2020-08-01'),
   (9, 1, '2020-09-01'),
   (10, 1, '2020-10-01'),
   (11, 1, '2020-11-01'),
   (12, 1, '2020-12-01');

INSERT INTO transactions (id, index, description, amount, date) VALUES
   (1, 1, 'Transaction', 1, '2020-01-01'),
   (2, 2, 'Transaction', 1, '2020-01-01'),
   (3, 3, 'Transaction', 1, '2020-01-01'),
   (4, 4, 'Transaction', 1, '2020-01-01'),
   (5, 5, 'Transaction', 1, '2020-01-01'),
   (6, 6, 'Transaction', 1, '2020-01-01'),
   (7, 7, 'Transaction', 1, '2020-01-01'),
   (8, 8, 'Transaction', 1, '2020-01-01'),
   (9, 9, 'Transaction', 1, '2020-01-01'),
   (10, 10, 'Transaction', 1, '2020-01-01'),
   (11, 11, 'Transaction', 1, '2020-01-01'),
   (12, 12, 'Transaction', 1, '2020-01-01');

INSERT INTO fee_payments (fee_id, transaction_id) VALUES
   (1, 1),
   (2, 2),
   (3, 3),
   (4, 4),
   (5, 5),
   (6, 6),
   (7, 7),
   (8, 8),
   (9, 9),
   (10, 10),
   (11, 11),
   (12, 12);