delete from users;
delete from orders;
delete from book;
delete from publisher;
delete from store;
delete from owner;
delete from book_order;
delete from user_order;

insert into users values ('1','John Doe', '123 Pine Crescent', '123-456-7890');
insert into store values ('1', 'Bookstore', '456 Road', '123-456-7890', 'bookstore@gmail.com');
insert into publisher values ('1', 'Publisher', 'Publisher address', 'bank account', 2.5, 'publisher email');
insert into book values ('1', 'title', 'author', 'genre', 100, 100, '1', '1');
insert into owner values ('1', 'name', 'address', 'phone', '1');
insert into orders values ('1', 12, 'ABK43HKKJHKL', 'SHIPPED', 'Canada Post', 'John Doe', '123 Pine Crescent', 'John Doe', '123 Street', 'CREDIT', '1');
insert into book_order values ('1', '1');
insert into user_order values ('1', '1');