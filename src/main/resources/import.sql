INSERT INTO Customer (id, name, email) VALUES (1, 'Mauricio', 'mauricio@email.com');
INSERT INTO Profile (id, address, phone, customer_id) VALUES (1, 'Rua A', '11999999999', 1);
INSERT INTO Product (id, name, price) VALUES (1, 'Notebook', 3500.00);
INSERT INTO Product (id, name, price) VALUES (2, 'Mouse', 150.00);
INSERT INTO Orders (id, status, customer_id) VALUES (1, 'NEW', 1);
INSERT INTO order_product (order_id, product_id) VALUES (1, 1);
INSERT INTO order_product (order_id, product_id) VALUES (1, 2);
