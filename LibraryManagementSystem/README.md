# Library Management System

Fresher-friendly Core Java + JDBC + MySQL console project.

## Features
- Book CRUD
- Member CRUD
- Borrow and return books
- Availability tracking
- Borrowing history
- Reports using Java Streams
- Custom exceptions
- JDBC transactions

## Requirements
- JDK 17+
- MySQL 8+
- Eclipse or IntelliJ
- MySQL Connector/J

## Setup
1. Execute `database/library_management.sql` in MySQL Workbench.
2. Open `src/com/library/config/DBConnection.java`.
3. Change USER/PASSWORD to your MySQL credentials.
4. Add MySQL Connector/J to the project classpath.
5. Run `com.library.Main`.

Default database:
`jdbc:mysql://localhost:3306/library_management`

## Eclipse
Import as Existing Projects into Workspace. Put `mysql-connector-j.jar` inside `lib/` and refresh the project.

## Demo
1. List books.
2. Add/list a member.
3. Borrow a book.
4. List books and show available copies decreased.
5. View borrowing history.
6. Return the book.
7. Show availability restored and fine calculation.
8. Open Reports.

## Concepts demonstrated
OOP, encapsulation, collections, exceptions, JDBC, PreparedStatement, ResultSet, SQL CRUD, JOIN, transactions, LocalDate, Streams and Lambda.
