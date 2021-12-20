create table store
(
    store_id varchar(20),
    store_name varchar(50),
    address varchar(100),
    phone_number varchar(20),
    email varchar(50),
    primary key (store_id)
);

create table users
(
    user_id   varchar(20),
    name       varchar(50),
    address    varchar(100),
    phone_number    varchar(20),
    primary key (user_id)
);

create table publisher
(
    publisher_id  varchar(20),
    publisher_name varchar(50),
    address varchar(100),
    bank_account  varchar(20),
    sales_percentage numeric(3,2) check (sales_percentage > 0),
    email varchar(50),
    primary key (publisher_id)
);

create table owner
(
    owner_id varchar(20),
    name varchar(50),
    address varchar(100),
    phone_number varchar(50),
    email varchar(50),
    primary key (owner_id)
);

create table orders
(
    order_id   varchar(20),
    total_price    numeric(8,2) check (total_price > 0),
    tracking_number    varchar(50),
    status    varchar(10) check (status in ('ACTIVE','SHIPPED','DELIVERED')),
    shipping_carrier  varchar(20),
    shipping_name   varchar(50),
    shipping_address   varchar(100),
    billing_name     varchar(50),
    billing_address varchar(100),
    billing_payment  varchar(50),
    store_id  varchar(20),
    primary key (order_id),
    foreign key (store_id) references store on delete cascade
);

create table book
(
    ISBN   varchar(20),
    title  varchar(50),
    author  varchar(50),
    genre   varchar(50),
    pages   int check (pages > 0),
    price   numeric(8,2) check (price > 0),
    publisher_id  varchar(20),
    store_id varchar(20),
    primary key (ISBN),
    foreign key (publisher_id) references publisher on delete cascade,
    foreign key (store_id) references store on delete cascade
);

create table book_order
(
    ISBN varchar(20),
    order_id varchar(20),
    primary key (ISBN, order_id),
    foreign key (ISBN) references book on delete cascade,
    foreign key (order_id) references orders on delete cascade
);

create table user_order
(
    user_id varchar(20),
    order_id varchar(20),
    primary key (user_id, order_id),
    foreign key (user_id) references users on delete cascade,
    foreign key (order_id) references orders on delete cascade
);
