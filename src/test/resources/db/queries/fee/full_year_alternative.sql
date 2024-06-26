INSERT INTO association.fees (id, person_id, date) VALUES
   (30, 2, '2020-01-01'),
   (31, 2, '2020-02-01'),
   (32, 2, '2020-03-01'),
   (33, 2, '2020-04-01'),
   (34, 2, '2020-05-01'),
   (35, 2, '2020-06-01'),
   (36, 2, '2020-07-01'),
   (37, 2, '2020-08-01'),
   (38, 2, '2020-09-01'),
   (39, 2, '2020-10-01'),
   (40, 2, '2020-11-01'),
   (41, 2, '2020-12-01');

INSERT INTO association.transactions (id, index, description, amount, date) VALUES
   (30, 30, 'Transaction', 1, '2020-01-01'),
   (31, 31, 'Transaction', 1, '2020-01-01'),
   (32, 32, 'Transaction', 1, '2020-01-01'),
   (33, 33, 'Transaction', 1, '2020-01-01'),
   (34, 34, 'Transaction', 1, '2020-01-01'),
   (35, 35, 'Transaction', 1, '2020-01-01'),
   (36, 36, 'Transaction', 1, '2020-01-01'),
   (37, 37, 'Transaction', 1, '2020-01-01'),
   (38, 38, 'Transaction', 1, '2020-01-01'),
   (39, 39, 'Transaction', 1, '2020-01-01'),
   (40, 40, 'Transaction', 1, '2020-01-01'),
   (41, 41, 'Transaction', 1, '2020-01-01');

INSERT INTO association.fee_payments (fee_id, transaction_id) VALUES
   (30, 30),
   (31, 31),
   (32, 32),
   (33, 33),
   (34, 34),
   (35, 35),
   (36, 36),
   (37, 37),
   (38, 38),
   (39, 39),
   (40, 40),
   (41, 41);