package com.library.service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book save(Book book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new DataIntegrityViolationException(
                "A book with ISBN '" + book.getIsbn() + "' already exists.");
        }
        return bookRepository.save(book);
    }

    public Book update(Long id, Book updated, Long authorId) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        // Check ISBN uniqueness (allow same ISBN on same book)
        Optional<Book> byIsbn = bookRepository.findByIsbn(updated.getIsbn());
        if (byIsbn.isPresent() && !byIsbn.get().getId().equals(id)) {
            throw new DataIntegrityViolationException(
                "Another book with ISBN '" + updated.getIsbn() + "' already exists.");
        }

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + authorId));

        existing.setTitle(updated.getTitle());
        existing.setIsbn(updated.getIsbn());
        existing.setGenre(updated.getGenre());
        existing.setPublishYear(updated.getPublishYear());
        existing.setPrice(updated.getPrice());
        existing.setAuthor(author);
        return bookRepository.save(existing);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookAuthorDTO> findAllBooksWithAuthors() {
        return bookRepository.findAllBooksWithAuthors();
    }

    public List<BookAuthorDTO> findByGenreWithAuthor(String genre) {
        return bookRepository.findByGenreWithAuthor(genre);
    }

    public List<Book> findByAuthorId(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }
}
