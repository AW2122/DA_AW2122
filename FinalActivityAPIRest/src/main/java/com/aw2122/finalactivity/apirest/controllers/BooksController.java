package com.aw2122.finalactivity.apirest.controllers;

import com.aw2122.finalactivity.apirest.models.BooksDAO;
import com.aw2122.finalactivity.apirest.models.BooksEntity;
import com.aw2122.finalactivity.apirest.models.ReservationsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aw2122-api-rest/books")
public class BooksController {
    @Autowired
    private BooksDAO booksDAO;

    @GetMapping
    public List<BooksEntity> findAllBooks() {
        return (List<BooksEntity>) booksDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BooksEntity> findBookByIsbn(@PathVariable(value = "isbn") String isbn) {
        Optional<BooksEntity> book = booksDAO.findById(isbn);

        if (book.isPresent()) {
            return ResponseEntity.ok().body(book.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public BooksEntity saveBook (@Validated @RequestBody BooksEntity book) {
        return booksDAO.save(book);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<?> deleteBook (@PathVariable(value = "isbn") String isbn) {
        Optional<BooksEntity> book = booksDAO.findById(isbn);
        if (book.isPresent()) {
            booksDAO.deleteById(isbn);
            return ResponseEntity.ok().body("Deleted");
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
