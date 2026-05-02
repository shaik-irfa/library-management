package com.library.repository;

import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByGenre(String genre);

    List<Book> findByAuthorId(Long authorId);

    // Custom INNER JOIN query between Book and Author
    @Query("SELECT new com.library.entity.BookAuthorDTO(" +
           "b.id, b.title, b.isbn, b.genre, b.publishYear, b.price, " +
           "a.id, a.name, a.nationality) " +
           "FROM Book b INNER JOIN b.author a ORDER BY a.name, b.title")
    List<BookAuthorDTO> findAllBooksWithAuthors();

    // Find books by genre with author info
    @Query("SELECT new com.library.entity.BookAuthorDTO(" +
           "b.id, b.title, b.isbn, b.genre, b.publishYear, b.price, " +
           "a.id, a.name, a.nationality) " +
           "FROM Book b INNER JOIN b.author a WHERE b.genre = :genre")
    List<BookAuthorDTO> findByGenreWithAuthor(@Param("genre") String genre);

    boolean existsByIsbn(String isbn);
}
