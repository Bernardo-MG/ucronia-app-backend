INSERT INTO association.fees (id, person_id, date) VALUES
   (1, 1, '2020-02-01'),
   (2, 2, '2020-03-01'),
   (3, 3, '2020-04-01'),
   (4, 4, '2020-05-01'),
   (5, 5, '2020-06-01');

INSERT INTO association.transactions (id, index, description, amount, date) VALUES
   (1, 10, 'Transaction', 1, '2020-01-01'),
   (2, 20, 'Transaction', 1, '2020-01-01'),
   (3, 30, 'Transaction', 1, '2020-01-01'),
   (4, 40, 'Transaction', 1, '2020-01-01');

INSERT INTO association.fee_payments (fee_id, transaction_id) VALUES
   (1, 1),
   (2, 2),
   (3, 3),
   (4, 4);