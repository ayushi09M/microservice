CREATE TABLE transaction(
    Id INT AUTO_INCREMENT PRIMARY KEY,
    pan_number VARCHAR(30),
    amount DOUBLE,
    payment_mode VARCHAR(30),
    timestamp TIMESTAMP,
    status VARCHAR(30)
);
