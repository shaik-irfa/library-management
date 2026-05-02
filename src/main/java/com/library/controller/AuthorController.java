package com.library.controller;

import com.library.entity.Author;
import com.library.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    // LIST
    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "author/list";
    }

    // SHOW ADD FORM
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("author", new Author());
        return "author/form";
    }

    // HANDLE ADD
    @PostMapping("/add")
    public String addAuthor(@Valid @ModelAttribute("author") Author author,
                            BindingResult result,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        if (result.hasErrors()) {
            return "author/form";
        }
        try {
            authorService.save(author);
            redirectAttributes.addFlashAttribute("successMsg", "Author added successfully!");
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMsg", "Data integrity error: " + e.getMostSpecificCause().getMessage());
            return "author/form";
        }
        return "redirect:/authors";
    }

    // SHOW EDIT FORM
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return authorService.findById(id)
                .map(author -> {
                    model.addAttribute("author", author);
                    return "author/form";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("errorMsg", "Author not found!");
                    return "redirect:/authors";
                });
    }

    // HANDLE UPDATE
    @PostMapping("/edit/{id}")
    public String updateAuthor(@PathVariable Long id,
                               @Valid @ModelAttribute("author") Author author,
                               BindingResult result,
                               RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "author/form";
        }
        try {
            authorService.update(id, author);
            ra.addFlashAttribute("successMsg", "Author updated successfully!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMsg", "Error updating author: " + e.getMessage());
        }
        return "redirect:/authors";
    }
}
