# 📚 Library Management System

A Spring Boot web application for managing a library's books and authors with a user-friendly JSP interface. The system demonstrates modern Java enterprise patterns with validation, custom queries, and comprehensive testing.

## ✨ Features

- **Book Management** — Create, read, and update books with ISBN uniqueness validation
- **Author Management** — Manage authors with search and nationality filtering
- **Inner Join Queries** — View books with complete author information in one query
- **Form Validation** — Server-side validation with error messages
- **Genre Classification** — Books organized by genre with color-coded badges
- **Responsive UI** — Clean, modern interface with Segoe UI styling
- **H2 Database Console** — Development database inspection at `/h2-console`
- **Sample Data** — Auto-populated with 10 authors and 10 books on startup

---

## 🛠️ Tech Stack

| Component       | Technology              | Version |
| --------------- | ----------------------- | ------- |
| **Framework**   | Spring Boot             | 3.2.0   |
| **Language**    | Java                    | 17      |
| **Build Tool**  | Maven                   | 3.9.6   |
| **Database**    | H2 (In-Memory)          | Latest  |
| **ORM**         | Hibernate / JPA         | 6.3.1   |
| **View Engine** | JSP + JSTL              | Jakarta |
| **Validation**  | Jakarta Bean Validation | Latest  |
| **Testing**     | JUnit 5 + Mockito       | Latest  |

---

## 📁 Project Structure

```
library-management/
├── src/
│   ├── main/
│   │   ├── java/com/library/
│   │   │   ├── LibraryApplication.java          # Spring Boot entry point
│   │   │   ├── controller/
│   │   │   │   ├── HomeController.java          # Routes "/" → "/books"
│   │   │   │   ├── AuthorController.java        # Author CRUD endpoints
│   │   │   │   └── BookController.java          # Book CRUD endpoints
│   │   │   ├── entity/
│   │   │   │   ├── Author.java                  # Author JPA entity
│   │   │   │   ├── Book.java                    # Book JPA entity
│   │   │   │   └── BookAuthorDTO.java           # DTO for book + author joins
│   │   │   ├── repository/
│   │   │   │   ├── AuthorRepository.java        # Author data access + custom queries
│   │   │   │   └── BookRepository.java          # Book data access + joins
│   │   │   └── service/
│   │   │       ├── AuthorService.java           # Author business logic
│   │   │       ├── BookService.java             # Book business logic
│   │   │       └── DataInitializer.java         # Sample data seeding
│   │   ├── resources/
│   │   │   └── application.properties           # App configuration
│   │   └── webapp/WEB-INF/views/
│   │       ├── author/
│   │       │   ├── list.jsp                     # Author list table
│   │       │   └── form.jsp                     # Author add/edit form
│   │       └── book/
│   │           ├── list.jsp                     # Book list with authors
│   │           └── form.jsp                     # Book add/edit form
│   └── test/
│       └── java/com/library/
│           ├── service/
│           │   ├── AuthorServiceTest.java       # 6 unit tests
│           │   └── BookServiceTest.java         # 7 unit tests
│           └── repository/
│               └── BookRepositoryTest.java      # 6 integration tests
├── pom.xml                                       # Maven dependencies
└── README.md                                     # This file
```

---

## 🚀 Getting Started

### Prerequisites

- **Java 17** (OpenJDK or Oracle JDK)
- **Maven 3.8+**
- **Git** (optional)

### Installation

1. **Clone or extract the project:**

   ```bash
   cd library-management
   ```

2. **Build the project:**

   ```bash
   mvn clean install
   ```

3. **Run the application:**

   ```bash
   mvn spring-boot:run
   ```

4. **Access the application:**
   - **Main App:** http://localhost:8080
   - **Books:** http://localhost:8080/books
   - **Authors:** http://localhost:8080/authors
   - **H2 Console:** http://localhost:8080/h2-console

---

## 📖 API & Endpoints

### Author Endpoints

| Method | Endpoint             | Description          |
| ------ | -------------------- | -------------------- |
| `GET`  | `/authors`           | List all authors     |
| `GET`  | `/authors/add`       | Show add author form |
| `POST` | `/authors/add`       | Save new author      |
| `GET`  | `/authors/edit/{id}` | Show edit form       |
| `POST` | `/authors/edit/{id}` | Update author        |

### Book Endpoints

| Method | Endpoint           | Description                                  |
| ------ | ------------------ | -------------------------------------------- |
| `GET`  | `/books`           | List all books (with authors via inner join) |
| `GET`  | `/books/add`       | Show add book form                           |
| `POST` | `/books/add`       | Save new book                                |
| `GET`  | `/books/edit/{id}` | Show edit form                               |
| `POST` | `/books/edit/{id}` | Update book                                  |

---

## 🗄️ Database Schema

### Authors Table

```sql
CREATE TABLE authors (
    id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name VARCHAR(255) NOT NULL,
    nationality VARCHAR(255) NOT NULL,
    birth_year INTEGER,
    bio VARCHAR(500)
);
```

### Books Table

```sql
CREATE TABLE books (
    id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    title VARCHAR(200) NOT NULL,
    isbn VARCHAR(255) NOT NULL UNIQUE,
    genre VARCHAR(255) NOT NULL,
    publish_year INTEGER CHECK (publish_year >= 1000),
    price DOUBLE,
    author_id BIGINT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES authors(id)
);
```

---

## 📊 Sample Data

The application auto-initializes with:

**10 Authors:**

- George Orwell, J.K. Rowling, Haruki Murakami, Toni Morrison, Gabriel García Márquez
- Chimamanda Ngozi Adichie, Leo Tolstoy, Virginia Woolf, Kazuo Ishiguro, Cormac McCarthy

**10 Books:**

- 1984, Animal Farm, Harry Potter and the Sorcerer's Stone, Norwegian Wood, Beloved
- One Hundred Years of Solitude, Purple Hibiscus, War and Peace, Mrs Dalloway, Never Let Me Go

---

## ✅ Testing

### Run All Tests

```bash
mvn test
```

### Test Coverage

**Unit Tests (13 tests):**

- `AuthorServiceTest.java` — 6 tests for CRUD and search operations
- `BookServiceTest.java` — 7 tests including ISBN validation and joins

**Integration Tests (6 tests):**

- `BookRepositoryTest.java` — 6 tests for repository queries and inner joins

### Example Test

```java
@Test
void findAllBooksWithAuthors_shouldReturnJoinResults() {
    List<BookAuthorDTO> results = bookRepository.findAllBooksWithAuthors();
    assertThat(results).hasSize(2);
    assertThat(results).allMatch(dto -> dto.getAuthorName().equals("George Orwell"));
}
```

---

## 🔐 Validation Rules

### Author Entity

- **Name** — Required, 2-100 characters
- **Nationality** — Required
- **Birth Year** — Optional integer
- **Bio** — Optional, max 500 characters

### Book Entity

- **Title** — Required, 1-200 characters
- **ISBN** — Required, unique constraint
- **Genre** — Required
- **Publish Year** — Min 1000
- **Price** — Min 0.0
- **Author** — Required foreign key reference

---

## 🎨 UI Features

### Book List View

- Table displaying books with authors (INNER JOIN)
- Genre badges with color coding:
  - 🔴 Dystopian (pink)
  - 💜 Fantasy (purple)
  - 📖 Literary (purple-ish)
  - 🟡 Historical (yellow)
  - ❌ Satire (red)
  - 🌟 Magical Realism (cyan)
  - 🎨 Modernist (blue)
- Edit button for each book
- Add Book button in toolbar
- Success/error alert messages

### Author List View

- Table with author details (name, nationality, birth year, bio)
- Edit button for each author
- Add Author button
- Responsive design with hover effects

### Forms

- Validation error messages inline
- Required field indicators (\*)
- Genre dropdown for books
- Author selection dropdown
- Cancel button to return without saving

---

## ⚙️ Configuration

### application.properties

```properties
# DataSource - H2 In-Memory
spring.datasource.url=jdbc:h2:mem:librarydb;DB_CLOSE_DELAY=-1
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA / Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# H2 Console (dev only)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JSP View Resolver
spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp
```

---

## 🔍 Key Implementations

### Custom Join Query

```java
@Query("SELECT new com.library.entity.BookAuthorDTO(" +
       "b.id, b.title, b.isbn, b.genre, b.publishYear, b.price, " +
       "a.id, a.name, a.nationality) " +
       "FROM Book b INNER JOIN b.author a ORDER BY a.name, b.title")
List<BookAuthorDTO> findAllBooksWithAuthors();
```

### ISBN Uniqueness Validation

```java
public Book save(Book book) {
    if (bookRepository.existsByIsbn(book.getIsbn())) {
        throw new DataIntegrityViolationException(
            "A book with ISBN '" + book.getIsbn() + "' already exists.");
    }
    return bookRepository.save(book);
}
```

### Service Layer Pattern

```java
@Service
@Transactional
public class BookService {
    // Dependency injection via constructor
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    // CRUD operations with validation
    public Book save(Book book) { ... }
    public Book update(Long id, Book updated, Long authorId) { ... }
    public void deleteById(Long id) { ... }
}
```

---

## 🐛 Error Handling

- **404 Errors** — Redirected with flash messages when entity not found
- **Validation Errors** — Form errors displayed inline with field binding
- **Data Integrity** — Duplicate ISBN detection with user-friendly messages
- **Transaction Management** — @Transactional ensures consistent database state

---

## 📈 Future Enhancements

- [ ] Add delete buttons with confirmation dialogs
- [ ] Implement book search by genre and author
- [ ] Add pagination for large datasets
- [ ] Create REST API endpoints (optional)
- [ ] Add user authentication and authorization
- [ ] Export books/authors to CSV/PDF
- [ ] Add book cover images
- [ ] Implement review/rating system
- [ ] Add reading list feature

---

## 📝 Notes

- **Database:** H2 in-memory database (data resets on restart)
- **Deployment:** For production, replace H2 with PostgreSQL, MySQL, or Oracle
- **View Resolver:** JSP files configured with `/WEB-INF/views/` prefix
- **Validation:** Server-side validation using Jakarta Bean Validation
- **Transactions:** All service methods are transactional for consistency

---

## 🤝 Contributing

Suggestions and improvements are welcome! Please:

1. Test all changes with `mvn test`
2. Ensure no compilation errors with `mvn clean compile`
3. Follow existing code style and patterns

---

## 📄 License

This project is open source and available for educational purposes.

---

## ✨ Status

✅ **Fully Functional** — All features working, no errors, sample data loaded, tests passing

**Last Updated:** May 2, 2026
**Java Version:** 17
**Spring Boot Version:** 3.2.0
#   l i b r a r y - m a n a g e m e n t  
 #   l i b r a r y - m a n a g e m e n t  
 