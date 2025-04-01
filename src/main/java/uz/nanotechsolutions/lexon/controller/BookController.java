package uz.nanotechsolutions.lexon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.nanotechsolutions.lexon.entity.Book;
import uz.nanotechsolutions.lexon.payload.request.BookRequest;
import uz.nanotechsolutions.lexon.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody BookRequest request) {
        bookService.save(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<Book>> findAllBooks() {
        return ResponseEntity.ok(bookService.findAll());
    }
}
