create table service_order_items (
    id               serial primary key,
    name             varchar(255)   not null,
    quantity         int            not null,
    service_id       int,
    unit_price_gross numeric(19, 2) not null,
    unit_price_net   numeric(19, 2) not null,
    order_id         int,
    foreign key (service_id) references services (id)
)
