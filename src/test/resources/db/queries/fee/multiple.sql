INSERT INTO fees (id, member_id, date, paid) VALUES
   (1, 1, '2020-02-01', true),
   (2, 2, '2020-03-01', true),
   (3, 3, '2020-04-01', true),
   (4, 4, '2020-05-01', true),
   (5, 5, '2020-06-01', false);

INSERT INTO transactions (id, index, description, amount, date) VALUES
   (1, 1, 'Transaction', 1, '2020-01-01'),
   (2, 2, 'Transaction', 1, '2020-01-01'),
   (3, 3, 'Transaction', 1, '2020-01-01'),
   (4, 4, 'Transaction', 1, '2020-01-01');

INSERT INTO fee_payments (fee_id, transaction_id) VALUES
   (1, 1),
   (2, 2),
   (3, 3),
   (4, 4);