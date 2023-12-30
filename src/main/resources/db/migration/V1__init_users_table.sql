create table users (
    id        serial primary key,
    email     varchar(255) not null,
    enabled   boolean      not null,
    full_name varchar(255) not null,
    password  varchar(255) not null,
    role      varchar(20)  not null,
    username  varchar(255) not null
);
