INSERT INTO fees (id, member_id, date) VALUES
   (2, 2, '2020-02-01');

INSERT INTO transactions (id, index, description, amount, date) VALUES
   (2, 2, 'Transaction', 1, '2020-01-01');

INSERT INTO fee_payments (fee_id, transaction_id) VALUES
   (2, 2);