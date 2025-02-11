-- Note: requirements를 위해 만든 SQL
CREATE TABLE user_entity(
    idx BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255),
    name VARCHAR(255),
    nickname VARCHAR(255),
    password VARCHAR(255),
    phone VARCHAR(255),
    enabled bit(1),
    gender VARCHAR(255)
);

CREATE TABLE order_entity(
    idx BIGINT(20) PRIMARY KEY AUTO_INCREMENT
    order_date DATE,
    order_id VARCHAR(255),
    product_name VARCHAR(255),
    user_idx BIGINT(20),
    FOREIGN KEY user_idx REFERENCES user_entity(idx)
);