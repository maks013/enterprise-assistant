create table clients (
    id                serial primary key,
    company_name      varchar(255) not null,
    tax_id_number     varchar(255) not null,
    address_id        int,
    representative_id int,
    foreign key (address_id) references clients_addresses (id),
    foreign key (representative_id) references clients_representatives (id)
);
