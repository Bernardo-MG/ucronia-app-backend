INSERT INTO association.transactions (id, index, description, amount, date) VALUES
   (2, 20, 'Cuota de Profile 2 Last name 2 para Febrero 2020', 2, '2020-02-01');

INSERT INTO association.fees (id, member_id, date, paid, fee_type_id, transaction_id) VALUES
   (2, 2, '2020-02-01', true, 1, 2);
