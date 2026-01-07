INSERT INTO association.transactions (id, index, description, amount, date) VALUES
   (1, 10, 'Cuota de Profile 1 Last name 1 para Febrero 2020', 2, '2020-02-01');

INSERT INTO association.fees (id, member_id, month, paid, fee_type_id, transaction_id) VALUES
   (1, 1, '2020-02-01', true, 1, 1),
   (2, 1, '2020-03-01', false, 1, null);

