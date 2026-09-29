CREATE DATABASE IF NOT EXISTS library_management;
USE library_management;

DROP TABLE IF EXISTS borrow_records;
DROP TABLE IF EXISTS members;
DROP TABLE IF EXISTS books;

CREATE TABLE books (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    author VARCHAR(100) NOT NULL,
    isbn VARCHAR(30) NOT NULL UNIQUE,
    category VARCHAR(80) NOT NULL,
    total_copies INT NOT NULL,
    available_copies INT NOT NULL
);

CREATE TABLE members (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    phone VARCHAR(20),
    joined_date DATE NOT NULL
);

CREATE TABLE borrow_records (
    id INT PRIMARY KEY AUTO_INCREMENT,
    book_id INT NOT NULL,
    member_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE,
    status ENUM('BORROWED','RETURNED') NOT NULL DEFAULT 'BORROWED',
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (member_id) REFERENCES members(id)
);

INSERT INTO books(title,author,isbn,category,total_copies,available_copies) VALUES
('Effective Java','Joshua Bloch','9780134685991','Programming',3,3),
('Clean Code','Robert C. Martin','9780132350884','Programming',2,2),
('Head First Java','Kathy Sierra','9780596009205','Programming',4,4),
('Database System Concepts','Abraham Silberschatz','9780078022159','Database',2,2);

INSERT INTO members(name,email,phone,joined_date)
VALUES ('Demo Member','demo.member@example.com','9876543210',CURRENT_DATE);
