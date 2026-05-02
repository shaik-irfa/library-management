package com.library.service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock private BookRepository bookRepository;
    @Mock private AuthorRepository authorRepository;
    @InjectMocks private BookService bookService;

    private Author author;
    private Book book;

    @BeforeEach
    void setUp() {
        author = new Author("George Orwell", "British", 1903, "Novelist.");
        author.setId(1L);
        book = new Book("1984", "978-0451524935", "Dystopian", 1949, 12.99, author);
        book.setId(1L);
    }

    @Test
    void findAll_shouldReturnAllBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(book));
        List<Book> result = bookService.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("1984");
    }

    @Test
    void save_newIsbn_shouldSaveSuccessfully() {
        when(bookRepository.existsByIsbn(anyString())).thenReturn(false);
        when(bookRepository.save(book)).thenReturn(book);
        Book saved = bookService.save(book);
        assertThat(saved.getIsbn()).isEqualTo("978-0451524935");
    }

    @Test
    void save_duplicateIsbn_shouldThrowException() {
        when(bookRepository.existsByIsbn("978-0451524935")).thenReturn(true);
        assertThatThrownBy(() -> bookService.save(book))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void findById_existing_shouldReturnBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Optional<Book> result = bookService.findById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getGenre()).isEqualTo("Dystopian");
    }

    @Test
    void update_existingBook_shouldUpdateSuccessfully() {
        Book updated = new Book("Nineteen Eighty-Four", "978-0451524935", "Dystopian", 1949, 15.00, author);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.findByIsbn("978-0451524935")).thenReturn(Optional.of(book)); // same book
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Book result = bookService.update(1L, updated, 1L);
        assertThat(result.getTitle()).isEqualTo("Nineteen Eighty-Four");
        assertThat(result.getPrice()).isEqualTo(15.00);
    }

    @Test
    void findAllBooksWithAuthors_shouldCallJoinQuery() {
        BookAuthorDTO dto = new BookAuthorDTO(
                1L, "1984", "978-0451524935", "Dystopian", 1949, 12.99,
                1L, "George Orwell", "British");
        when(bookRepository.findAllBooksWithAuthors()).thenReturn(List.of(dto));

        List<BookAuthorDTO> result = bookService.findAllBooksWithAuthors();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAuthorName()).isEqualTo("George Orwell");
        verify(bookRepository).findAllBooksWithAuthors();
    }

    @Test
    void findByAuthorId_shouldReturnBooksForAuthor() {
        when(bookRepository.findByAuthorId(1L)).thenReturn(List.of(book));
        List<Book> result = bookService.findByAuthorId(1L);
        assertThat(result).hasSize(1);
    }
}
