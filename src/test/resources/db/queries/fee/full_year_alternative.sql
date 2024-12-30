INSERT INTO association.transactions (id, index, description, amount, date) VALUES
   (30, 30, 'Transaction', 2, '2020-02-01'),
   (31, 31, 'Transaction', 2, '2020-02-01'),
   (32, 32, 'Transaction', 2, '2020-02-01'),
   (33, 33, 'Transaction', 2, '2020-02-01'),
   (34, 34, 'Transaction', 2, '2020-02-01'),
   (35, 35, 'Transaction', 2, '2020-02-01'),
   (36, 36, 'Transaction', 2, '2020-02-01'),
   (37, 37, 'Transaction', 2, '2020-02-01'),
   (38, 38, 'Transaction', 2, '2020-02-01'),
   (39, 39, 'Transaction', 2, '2020-02-01'),
   (40, 40, 'Transaction', 2, '2020-02-01'),
   (41, 41, 'Transaction', 2, '2020-02-01');

INSERT INTO association.fees (id, person_id, date, paid, transaction_id) VALUES
   (30, 2, '2020-01-01', true, 30),
   (31, 2, '2020-02-01', true, 31),
   (32, 2, '2020-03-01', true, 32),
   (33, 2, '2020-04-01', true, 33),
   (34, 2, '2020-05-01', true, 34),
   (35, 2, '2020-06-01', true, 35),
   (36, 2, '2020-07-01', true, 36),
   (37, 2, '2020-08-01', true, 37),
   (38, 2, '2020-09-01', true, 38),
   (39, 2, '2020-10-01', true, 39),
   (40, 2, '2020-11-01', true, 40),
   (41, 2, '2020-12-01', true, 41);
