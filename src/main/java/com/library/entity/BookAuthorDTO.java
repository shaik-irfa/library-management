package com.library.entity;

public class BookAuthorDTO {
    private Long bookId;
    private String bookTitle;
    private String isbn;
    private String genre;
    private Integer publishYear;
    private Double price;
    private Long authorId;
    private String authorName;
    private String nationality;

    public BookAuthorDTO(Long bookId, String bookTitle, String isbn, String genre,
                         Integer publishYear, Double price,
                         Long authorId, String authorName, String nationality) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.isbn = isbn;
        this.genre = genre;
        this.publishYear = publishYear;
        this.price = price;
        this.authorId = authorId;
        this.authorName = authorName;
        this.nationality = nationality;
    }

    public Long getBookId() { return bookId; }
    public String getBookTitle() { return bookTitle; }
    public String getIsbn() { return isbn; }
    public String getGenre() { return genre; }
    public Integer getPublishYear() { return publishYear; }
    public Double getPrice() { return price; }
    public Long getAuthorId() { return authorId; }
    public String getAuthorName() { return authorName; }
    public String getNationality() { return nationality; }
}
