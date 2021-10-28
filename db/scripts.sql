CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     name VARCHAR(50) NOT NULL,
                                     email VARCHAR(255) NOT NULL UNIQUE,
                                     password VARCHAR(100) NOT NULL,
                                     phone VARCHAR(25)
);

CREATE TABLE IF NOT EXISTS body (
                                          id SERIAL PRIMARY KEY,
                                          name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS brands (
                                      id SERIAL PRIMARY KEY,
                                      name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS posts (
                                   id SERIAL PRIMARY KEY,
                                   description TEXT NOT NULL,
                                   created TIMESTAMP NOT NULL,
                                   sale BOOLEAN NOT NULL,
                                   brand_id INT NOT NULL REFERENCES brands(id),
                                   body_id INT NOT NULL REFERENCES body(id),
                                   user_id INT NOT NULL REFERENCES users(id)
);