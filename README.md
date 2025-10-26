# Library-Management-system
developed a console based application to manage books in a library. Implemented features like adding books, issuing books, and returning books using Java and MySQL (via JDBC)
Features
- Add new books to the database  
- View all books with issue status  
- Issue or return a book  
- Uses MySQL for persistent data storage  


Technology Stack
- **Language:** Java  
- **Database:** MySQL  
- **Connector:** JDBC  
- **IDE:** Eclipse / IntelliJ IDEA  

Database Setup
```sql
CREATE DATABASE librarydb;
USE librarydb;

CREATE TABLE books (
    id INT PRIMARY KEY,
    title VARCHAR(100),
    author VARCHAR(100),
    issued BOOLEAN
);
