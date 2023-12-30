create table clients_representatives (
    id           serial primary key,
    client_id    int,
    email        varchar(255) not null,
    phone_number varchar(255) not null,
    name         varchar(255) not null,
    surname      varchar(255) not null
);
