INSERT INTO association.transactions (id, index, description, amount, date) VALUES
   (1, 10, 'Cuota de Contact 1 Last name 1 para Febrero 2020', 2, '2020-02-01');

INSERT INTO association.fees (id, contact_id, date, paid, transaction_id) VALUES
   (1, 1, '2020-02-01', true, 1),
   (2, 1, '2020-03-01', false, null);

