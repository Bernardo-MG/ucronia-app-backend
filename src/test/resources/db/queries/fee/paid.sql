INSERT INTO fees (id, person_id, date) VALUES
   (1, 1, '2020-02-01');

INSERT INTO transactions (id, index, description, amount, date) VALUES
   (1, 10, 'Transaction', 1, '2020-01-01');

INSERT INTO fee_payments (fee_id, transaction_id) VALUES
   (1, 1);