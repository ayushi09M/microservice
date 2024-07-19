INSERT INTO transaction(pan_number, amount, payment_mode, timestamp, status)
VALUES
    ('ABCPAN1234', 30000.50, 'Credit Card', '2023-10-19 12:00:00', 'Success'),
    ('XYZPAN5678', 5000.75, 'Debit Card', '2023-10-19 12:15:00', 'Success'),
    ('XYZPAN5678', 4000.75, 'Paytm', '2023-10-19 12:15:00', 'Success'),
    ('LMNPAN4321', 7500.25, 'PayPal', '2023-10-19 12:30:00', 'Pending');

