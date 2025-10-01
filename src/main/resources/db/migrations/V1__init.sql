CREATE TABLE if not exists todo(
    id serial primary key,
    title varchar(255) not null,
    description text,
    is_done bool default false
    )