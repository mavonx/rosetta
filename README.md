# rosetta

An e-commerce engine that demonstrates the evolution of data access, built to practice database connectivity by starting with raw JDBC, then refactoring to Hibernate, and finally refactoring to Spring Data JPA.

## schema

Run the following SQL script to create the schema used across all three stages:

```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE user_profiles (
    id SERIAL PRIMARY KEY,
    user_id INT UNIQUE NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    CONSTRAINT fk_user_profile
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL,
    category_id INT NOT NULL,
    CONSTRAINT fk_product_category
        FOREIGN KEY (category_id) REFERENCES categories(id)
        ON DELETE RESTRICT
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    status VARCHAR(50),
    total_amount DECIMAL(10, 2) NOT NULL,
    order_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_order_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE order_items (
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price_at_purchase DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_order_item_order
        FOREIGN KEY (order_id) REFERENCES orders(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_order_item_product
        FOREIGN KEY (product_id) REFERENCES products(id)
        ON DELETE RESTRICT
);
```
