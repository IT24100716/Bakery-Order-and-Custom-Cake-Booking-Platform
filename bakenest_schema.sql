CREATE DATABASE IF NOT EXISTS BakeNest;
USE BakeNest;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    phone VARCHAR(255),
    address VARCHAR(255),
    created_at DATETIME
);

CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    sku VARCHAR(20) UNIQUE,
    price DOUBLE NOT NULL,
    category VARCHAR(255),
    stock_type VARCHAR(20) DEFAULT 'STOCK',
    stock INT,
    active BOOLEAN DEFAULT TRUE,
    deleted BOOLEAN DEFAULT FALSE,
    image_url VARCHAR(255)
);

CREATE TABLE custom_cake_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    flavor VARCHAR(255),
    size VARCHAR(255),
    message VARCHAR(1000),
    reference_image_url VARCHAR(255),
    status VARCHAR(255) NOT NULL,
    quoted_price DOUBLE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_amount DOUBLE NOT NULL,
    status VARCHAR(50) NOT NULL,
    payment_status VARCHAR(50) NOT NULL,
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DOUBLE NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(1000),
    image_url LONGTEXT
);
