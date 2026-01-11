INSERT INTO association.transactions (id, index, description, amount, date) VALUES
   (1, 10, 'Transaction', 2, '2020-02-01'),
   (2, 20, 'Transaction', 2, '2020-02-01'),
   (3, 30, 'Transaction', 2, '2020-02-01'),
   (4, 40, 'Transaction', 2, '2020-02-01');

INSERT INTO association.fees (id, member_id, month, paid, fee_type_id, transaction_id) VALUES
   (1, 1, '2020-02-01', true, 1, 1),
   (2, 2, '2020-03-01', true, 1, 2),
   (3, 3, '2020-04-01', true, 1, 3),
   (4, 4, '2020-05-01', true, 1, 4),
   (5, 5, '2020-06-01', false, 1, null);
