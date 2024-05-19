INSERT INTO association.fees (person_id, date) VALUES
   (1, 1, '2020-02-01'),
   (2, 1, '2020-01-01');

INSERT INTO association.transactions (id, index, description, amount, date) VALUES
   (1, 1, 'Transaction', 1, '2020-01-01'),
   (2, 2, 'Transaction', 1, '2020-01-01');

INSERT INTO association.fee_payments (fee_id, transaction_id) VALUES
   (1, 1),
   (2, 2);