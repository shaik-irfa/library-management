package com.library.repository;

import com.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByNationality(String nationality);

    List<Author> findByNameContainingIgnoreCase(String name);

    @Query("SELECT a FROM Author a WHERE a.birthYear > :year ORDER BY a.birthYear ASC")
    List<Author> findAuthorsAfterYear(int year);
}
