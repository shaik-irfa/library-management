package com.library.service;

import com.library.entity.Author;
import com.library.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author("George Orwell", "British", 1903, "Novelist.");
        author.setId(1L);
    }

    @Test
    void findAll_shouldReturnAllAuthors() {
        when(authorRepository.findAll()).thenReturn(List.of(author));
        List<Author> result = authorService.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("George Orwell");
        verify(authorRepository).findAll();
    }

    @Test
    void findById_existingId_shouldReturnAuthor() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        Optional<Author> result = authorService.findById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getNationality()).isEqualTo("British");
    }

    @Test
    void findById_nonExistingId_shouldReturnEmpty() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Author> result = authorService.findById(99L);
        assertThat(result).isEmpty();
    }

    @Test
    void save_shouldPersistAndReturnAuthor() {
        when(authorRepository.save(author)).thenReturn(author);
        Author saved = authorService.save(author);
        assertThat(saved.getName()).isEqualTo("George Orwell");
        verify(authorRepository).save(author);
    }

    @Test
    void update_existingId_shouldUpdateFields() {
        Author updated = new Author("Eric Blair", "British", 1903, "Updated bio.");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenAnswer(inv -> inv.getArgument(0));

        Author result = authorService.update(1L, updated);

        assertThat(result.getName()).isEqualTo("Eric Blair");
        assertThat(result.getBio()).isEqualTo("Updated bio.");
    }

    @Test
    void update_nonExistingId_shouldThrowException() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> authorService.update(99L, author))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Author not found");
    }

    @Test
    void searchByName_shouldCallRepository() {
        when(authorRepository.findByNameContainingIgnoreCase("Orwell"))
                .thenReturn(List.of(author));
        List<Author> result = authorService.searchByName("Orwell");
        assertThat(result).hasSize(1);
    }
}
