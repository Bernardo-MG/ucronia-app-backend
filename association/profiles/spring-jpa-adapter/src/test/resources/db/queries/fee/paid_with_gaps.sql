INSERT INTO funds.transactions (id, index, description, amount, date) VALUES
   (1, 10, 'Paid fees with gaps', 2, '2020-05-01'),
   (2, 20, 'Paid fees with gaps', 2, '2020-05-01');

INSERT INTO funds.fees (id, member_id, month, paid, fee_type_id, transaction_id) VALUES
   (1, 1, '2020-01-01', false, 1, null),
   (2, 1, '2020-02-01', true, 1, 1),
   (3, 1, '2020-03-01', false, 1, null),
   (4, 1, '2020-04-01', true, 1, 2);
