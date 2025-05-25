CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    created_at DATETIME NOT NULL,
    modified_at DATETIME NOT NULL
);

CREATE TABLE schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    todo VARCHAR(200) NOT NULL,
    author VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    created_at DATETIME NOT NULL,
    modified_at DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

INSERT INTO user (email, created_at, modified_at) VALUES
    ('user1@example.com', NOW(), NOW()),
    ('user2@example.com', NOW(), NOW());