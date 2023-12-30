create table orders (
    id                     serial   primary key,
    client_id              int,
    created_at             timestamp   not null,
    days_to_pay            varchar(20) not null,
    deadline               timestamp   not null,
    payment                varchar(20) not null,
    status                 varchar(20) not null,
    user_id                int,
    additional_information varchar(255),
    foreign key (client_id) references clients (id),
    foreign key (user_id) references users (id)
);

