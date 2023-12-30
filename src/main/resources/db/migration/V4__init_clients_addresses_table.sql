create table clients_addresses (
    id          serial primary key,
    city        varchar(255) not null,
    client_id   int,
    postal_code varchar(255) not null,
    street      varchar(255) not null
);
