CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT NOW()
);

ALTER TABLE products ADD COLUMN category_id INT;
ALTER TABLE products ADD CONSTRAINT fk_category
    FOREIGN KEY (category_id)
    REFERENCES categories (id)
    ON DELETE SET NULL;