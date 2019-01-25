DROP TABLE IF EXISTS products;

CREATE TABLE products (
  id VARCHAR(100) PRIMARY KEY NOT NULL,
  name TEXT,
  model TEXT NOT NULL,
  amount INT NOT NULL,
  color TEXT
);

INSERT INTO products (id, name, model, amount, color)
VALUES('f3831f8c-c338-4ebe-a82a-e2fc1d1ff78a', 'Table', 'XXL', 5, 'black');


INSERT INTO products (id, name, model, amount, color)
VALUES('t9876f8c-c338-4abc-zf6a-ttt1', 'Bed', 'XL', 3, 'red');


INSERT INTO products (id, name, model, amount, color)
VALUES('38777179-7094-4200-9d61-edb101c6ea84', 'Desk', 'L', 2, 'white');


INSERT INTO products (id, name, model, amount, color)
VALUES('08dbe05-606e-4dad-9d33-90ef10e334f9', 'Chair', 'XXL', 4, 'brown');
