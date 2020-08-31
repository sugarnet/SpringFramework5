insert into customer (name, lastname, email, created_at, photo) values ('Diego', 'Scifo', 'dscifo@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Sol', 'Mauna', 'sol@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Pedro', 'Gómez', 'pgomez@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Pablo', 'Almeida', 'palmeida@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Walter', 'White', 'wwhite@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Koji', 'Kabuto', 'kkabuto@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Frodo', 'Bolsón', 'fbolson@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Bilbo', 'Bolsón', 'bbolson@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Diego', 'Rozo', 'drozo@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Leo', 'Suárez', 'lsuarez@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Martín', 'Soto', 'msoto@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Mariano', 'Cialone', 'mcialone@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Jhon', 'Rambo', 'jrambo@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Mia', 'Khalifa', 'mkhalifa@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Giancarlo', 'Espósito', 'gesposito@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Wanda', 'Nara', 'wnara@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Lio', 'Messi', 'lmessi@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Liu', 'Kang', 'lkang@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Jonny', 'Cage', 'jcage@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Kill', 'Bill', 'kbill@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Tony', 'Vallelonga', 'tvallelonga@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Don', 'Shirley', 'dshirley@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Ray', 'Liotta', 'rliotta@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Robert', 'De Niro', 'rdeniro@mail.com', '2020-08-04', '');
insert into customer (name, lastname, email, created_at, photo) values ('Joe', 'Pesci', 'jpesci@mail.com', '2020-08-04', '');

insert into product (description, price, created_at) values ('Azúcar x 1KG', 23.4, NOW());
insert into product (description, price, created_at) values ('Harina de Maiz x 500GRS', 9.0, NOW());
insert into product (description, price, created_at) values ('Pimienta Negra x 100 GRS', 223.23, NOW());
insert into product (description, price, created_at) values ('Agua Saborizada x 2L', 123.77, NOW());
insert into product (description, price, created_at) values ('Bicarbonato de Sodio x 25GRS', 3.4, NOW());

insert into bill (description, observation, customer_id, created_at) values ('Bill Description 1', 'Bill Observation 1', 1, now());
insert into bill_item (quantity, bill_id, product_id) values (2, 1, 1);
insert into bill_item (quantity, bill_id, product_id) values (3, 1, 2);
insert into bill_item (quantity, bill_id, product_id) values (1, 1, 3);
insert into bill_item (quantity, bill_id, product_id) values (4, 1, 4);

insert into bill (description, observation, customer_id, created_at) values ('Bill Description 2', 'Bill Observation 2', 1, now());
insert into bill_item (quantity, bill_id, product_id) values (4, 2, 1);
insert into bill_item (quantity, bill_id, product_id) values (3, 2, 4);

