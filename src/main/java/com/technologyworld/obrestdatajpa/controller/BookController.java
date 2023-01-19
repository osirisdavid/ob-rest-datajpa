package com.technologyworld.obrestdatajpa.controller;

import com.technologyworld.obrestdatajpa.entities.Book;
import com.technologyworld.obrestdatajpa.repository.BookRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    //CRUD sobre la entidad Book
    /**
     * http//:localhost:8080/api/books
     * @return List<Book>
     * */
    @GetMapping("/api/books")
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    /**
     * @param id
     * http//:localhost:8080/api/books/{id}
     * @return
     */
    @GetMapping("api/books/{id}")
    public ResponseEntity<Book> findById(@PathVariable("id") Long id){
        Optional<Book> bookOptional= bookRepository.findById(id);
        // Option 1
        if(bookOptional.isPresent())
            return ResponseEntity.ok(bookOptional.get());
        else
            return ResponseEntity.notFound().build();
        //Option 2
        // return bookOptional.orElse(null);
    }


    /**
     * http//:localhost:8080/api/books
     * @return Book
     */
    @PostMapping("/api/books")
    public ResponseEntity<Book> create(@RequestBody Book book, @RequestHeader HttpHeaders headers){
        System.out.println(headers.get("User-Agent"));
        System.out.println(headers.get("Content-Type"));
        // guardar el libro recibido por paramento a la db
        var bookCreated = bookRepository.save(book);
        if(bookCreated != null)
            return ResponseEntity.ok(bookCreated);
        else
            return ResponseEntity.notFound().build();
    }

    /**
     *
     *
     */

}
