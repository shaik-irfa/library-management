package com.library.repository;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired private BookRepository bookRepository;
    @Autowired private AuthorRepository authorRepository;

    private Author author;

    @BeforeEach
    void setUp() {
        author = authorRepository.save(
            new Author("George Orwell", "British", 1903, "Novelist."));
        bookRepository.save(
            new Book("1984", "978-001", "Dystopian", 1949, 12.99, author));
        bookRepository.save(
            new Book("Animal Farm", "978-002", "Satire", 1945, 9.99, author));
    }

    @Test
    void findByIsbn_shouldReturnBook() {
        Optional<Book> result = bookRepository.findByIsbn("978-001");
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("1984");
    }

    @Test
    void findByIsbn_nonExisting_shouldReturnEmpty() {
        Optional<Book> result = bookRepository.findByIsbn("999-999");
        assertThat(result).isEmpty();
    }

    @Test
    void findByGenre_shouldReturnCorrectBooks() {
        List<Book> dystopian = bookRepository.findByGenre("Dystopian");
        assertThat(dystopian).hasSize(1);
        assertThat(dystopian.get(0).getTitle()).isEqualTo("1984");
    }

    @Test
    void findAllBooksWithAuthors_shouldReturnJoinResults() {
        List<BookAuthorDTO> results = bookRepository.findAllBooksWithAuthors();
        assertThat(results).hasSize(2);
        assertThat(results).allMatch(dto -> dto.getAuthorName().equals("George Orwell"));
    }

    @Test
    void existsByIsbn_shouldReturnTrueForExisting() {
        assertThat(bookRepository.existsByIsbn("978-001")).isTrue();
        assertThat(bookRepository.existsByIsbn("978-999")).isFalse();
    }

    @Test
    void findByAuthorId_shouldReturnBooksForAuthor() {
        List<Book> books = bookRepository.findByAuthorId(author.getId());
        assertThat(books).hasSize(2);
    }
}
