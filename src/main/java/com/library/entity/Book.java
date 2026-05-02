package com.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 200)
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "ISBN is required")
    @Column(nullable = false, unique = true)
    private String isbn;

    @NotBlank(message = "Genre is required")
    @Column(nullable = false)
    private String genre;

    @Min(value = 1000, message = "Invalid publish year")
    @Column(name = "publish_year")
    private Integer publishYear;

    @DecimalMin(value = "0.0")
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    // Constructors
    public Book() {}

    public Book(String title, String isbn, String genre, Integer publishYear, Double price, Author author) {
        this.title = title;
        this.isbn = isbn;
        this.genre = genre;
        this.publishYear = publishYear;
        this.price = price;
        this.author = author;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public Integer getPublishYear() { return publishYear; }
    public void setPublishYear(Integer publishYear) { this.publishYear = publishYear; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }
}
