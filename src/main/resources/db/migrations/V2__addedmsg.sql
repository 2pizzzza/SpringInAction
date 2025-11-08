CREATE TABLE IF NOT EXISTS messages (
    id SERIAL PRIMARY KEY,
    "from_user" INT NOT NULL,
    "to_user" INT NOT NULL
)
