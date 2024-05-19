INSERT INTO association.fees (id, person_id, date) VALUES
   (2, 2, '2020-02-01');

INSERT INTO association.transactions (id, index, description, amount, date) VALUES
   (2, 20, 'Transaction', 1, '2020-01-01');

INSERT INTO association.fee_payments (fee_id, transaction_id) VALUES
   (2, 2);