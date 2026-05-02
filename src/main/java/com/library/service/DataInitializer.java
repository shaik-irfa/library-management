package com.library.service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private AuthorRepository authorRepository;
    @Autowired private BookRepository bookRepository;

    @Override
    public void run(String... args) {
        // === 10 Authors ===
        Author a1 = authorRepository.save(new Author("George Orwell",       "British",   1903, "Novelist known for dystopian fiction."));
        Author a2 = authorRepository.save(new Author("J.K. Rowling",        "British",   1965, "Author of the Harry Potter series."));
        Author a3 = authorRepository.save(new Author("Haruki Murakami",     "Japanese",  1949, "Surrealist contemporary fiction writer."));
        Author a4 = authorRepository.save(new Author("Toni Morrison",       "American",  1931, "Nobel Prize-winning novelist."));
        Author a5 = authorRepository.save(new Author("Gabriel García Márquez","Colombian",1927, "Pioneer of magical realism."));
        Author a6 = authorRepository.save(new Author("Chimamanda Ngozi Adichie","Nigerian",1977,"Feminist author and TED speaker."));
        Author a7 = authorRepository.save(new Author("Leo Tolstoy",         "Russian",   1828, "Epic novelist of the 19th century."));
        Author a8 = authorRepository.save(new Author("Virginia Woolf",      "British",   1882, "Modernist stream-of-consciousness writer."));
        Author a9 = authorRepository.save(new Author("Kazuo Ishiguro",      "British",   1954, "Nobel Prize winner, The Remains of the Day."));
        Author a10 = authorRepository.save(new Author("Cormac McCarthy",    "American",  1933, "Author of bleak, powerful American fiction."));

        // === 10 Books ===
        bookRepository.save(new Book("1984",                          "978-0451524935", "Dystopian",    1949, 12.99, a1));
        bookRepository.save(new Book("Animal Farm",                   "978-0451526342", "Satire",       1945,  9.99, a1));
        bookRepository.save(new Book("Harry Potter and the Sorcerer's Stone","978-0439708180","Fantasy",1997, 14.99, a2));
        bookRepository.save(new Book("Norwegian Wood",                "978-0375704024", "Literary",     1987, 13.50, a3));
        bookRepository.save(new Book("Beloved",                       "978-1400033416", "Historical",   1987, 15.00, a4));
        bookRepository.save(new Book("One Hundred Years of Solitude", "978-0060883287", "Magical Realism",1967,16.99,a5));
        bookRepository.save(new Book("Purple Hibiscus",               "978-1616953850", "Literary",     2003, 13.00, a6));
        bookRepository.save(new Book("War and Peace",                 "978-1400079988", "Historical",   1869, 19.99, a7));
        bookRepository.save(new Book("Mrs Dalloway",                  "978-0156628709", "Modernist",    1925, 11.00, a8));
        bookRepository.save(new Book("Never Let Me Go",               "978-1400078776", "Dystopian",    2005, 14.00, a9));

        System.out.println("=== Database seeded: 10 Authors, 10 Books ===");
    }
}
