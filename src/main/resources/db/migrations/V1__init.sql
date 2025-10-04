CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password TEXT NOT NULL,
    created_at TIMESTAMPTZ DEFAULT now(),
    updated_at TIMESTAMPTZ
    );

CREATE TABLE if not exists todo(
    id serial primary key,
    title varchar(255) not null,
    description text,
    is_done bool default false,
    user_id int not null,
    foreign key (user_id) references users(id)
    )