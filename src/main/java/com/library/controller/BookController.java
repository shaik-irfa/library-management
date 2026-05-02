package com.library.controller;

import com.library.entity.Book;
import com.library.service.AuthorService;
import com.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired private BookService bookService;
    @Autowired private AuthorService authorService;

    // LIST (inner join view)
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("bookAuthorList", bookService.findAllBooksWithAuthors());
        return "book/list";
    }

    // SHOW ADD FORM
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.findAll());
        return "book/form";
    }

    // HANDLE ADD
    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("book") Book book,
                          BindingResult result,
                          @RequestParam("authorId") Long authorId,
                          RedirectAttributes ra,
                          Model model) {
        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            return "book/form";
        }
        try {
            authorService.findById(authorId).ifPresent(book::setAuthor);
            bookService.save(book);
            ra.addFlashAttribute("successMsg", "Book added successfully!");
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMsg", "ISBN already exists: " + book.getIsbn());
            model.addAttribute("authors", authorService.findAll());
            return "book/form";
        }
        return "redirect:/books";
    }

    // SHOW EDIT FORM
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return bookService.findById(id)
                .map(book -> {
                    model.addAttribute("book", book);
                    model.addAttribute("authors", authorService.findAll());
                    model.addAttribute("selectedAuthorId", book.getAuthor().getId());
                    return "book/form";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("errorMsg", "Book not found!");
                    return "redirect:/books";
                });
    }

    // HANDLE UPDATE
    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id,
                             @Valid @ModelAttribute("book") Book book,
                             BindingResult result,
                             @RequestParam("authorId") Long authorId,
                             RedirectAttributes ra,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            return "book/form";
        }
        try {
            bookService.update(id, book, authorId);
            ra.addFlashAttribute("successMsg", "Book updated successfully!");
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMsg", e.getMessage());
            model.addAttribute("authors", authorService.findAll());
            return "book/form";
        }
        return "redirect:/books";
    }
}
