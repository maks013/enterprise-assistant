create table invoices (
    id                serial primary key,
    issue_date        timestamp,
    number            varchar(255),
    order_id          int,
    payment           varchar(255),
    payment_due_date  timestamp,
    total_price_gross numeric(19, 2),
    total_price_net   numeric(19, 2),
    foreign key (order_id) references orders (id)
)
