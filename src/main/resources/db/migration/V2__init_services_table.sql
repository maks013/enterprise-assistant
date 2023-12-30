create table services (
    id                     serial primary key,
    name                   varchar(255)   not null,
    price_gross            numeric(19, 2) not null,
    price_net              numeric(19, 2) not null,
    image_url              varchar(255),
    additional_information varchar(255)
);
