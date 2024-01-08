alter table clients
    add constraint fk_clients_addresses
        foreign key (address_id)
            references clients_addresses (id)
            on delete cascade;
