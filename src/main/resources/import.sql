-- Inserindo clientes
INSERT INTO customers (id, name, email) VALUES (1, 'Mauricio Silva', 'mauricio@email.com');
INSERT INTO customers (id, name, email) VALUES (2, 'Ana Costa', 'ana@email.com');
INSERT INTO customers (id, name, email) VALUES (3, 'João Santos', 'joao@email.com');

-- Inserindo perfis
INSERT INTO profiles (id, address, phone, city, state, zip_code, customer_id) VALUES (1, 'Rua das Flores, 123', '(11) 99999-9999', 'São Paulo', 'SP', '01234-567', 1);
INSERT INTO profiles (id, address, phone, city, state, zip_code, customer_id) VALUES (2, 'Avenida Paulista, 456', '(11) 88888-8888', 'São Paulo', 'SP', '01310-100', 2);
INSERT INTO profiles (id, address, phone, city, state, zip_code, customer_id) VALUES (3, 'Rua Augusta, 789', '(11) 77777-7777', 'São Paulo', 'SP', '01305-100', 3);

-- Inserindo produtos
INSERT INTO products (id, name, price, description) VALUES (1, 'Notebook Dell', 3500.00, 'Notebook Dell Inspiron com 8GB RAM');
INSERT INTO products (id, name, price, description) VALUES (2, 'Mouse Logitech', 150.00, 'Mouse sem fio Logitech M705');
INSERT INTO products (id, name, price, description) VALUES (3, 'Teclado Mecânico', 450.00, 'Teclado mecânico RGB com switches Cherry MX');
INSERT INTO products (id, name, price, description) VALUES (4, 'Monitor 24"', 1200.00, 'Monitor Full HD 24 polegadas');

-- Inserindo pedidos
INSERT INTO orders (id, status, order_date, total_amount, customer_id) VALUES (1, 'NEW', '2024-01-15 10:30:00', 3650.00, 1);
INSERT INTO orders (id, status, order_date, total_amount, customer_id) VALUES (2, 'PROCESSING', '2024-01-16 14:20:00', 450.00, 2);
INSERT INTO orders (id, status, order_date, total_amount, customer_id) VALUES (3, 'SHIPPED', '2024-01-17 09:15:00', 1650.00, 3);

-- Inserindo produtos nos pedidos (relacionamento many-to-many)
INSERT INTO order_product (order_id, product_id) VALUES (1, 1);
INSERT INTO order_product (order_id, product_id) VALUES (1, 2);
INSERT INTO order_product (order_id, product_id) VALUES (2, 3);
INSERT INTO order_product (order_id, product_id) VALUES (3, 2);
INSERT INTO order_product (order_id, product_id) VALUES (3, 4);
