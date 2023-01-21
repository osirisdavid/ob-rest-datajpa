package com.technologyworld.obrestdatajpa.controller;

import com.technologyworld.obrestdatajpa.entities.Book;
import com.technologyworld.obrestdatajpa.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class BookController {

    private final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    //CRUD sobre la entidad Book
    /**
     * Buscar todo los libros
     * http//:localhost:8080/api/books
     * @return List<Book>
     * */
    @GetMapping("/api/books")
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    /**
     * Buscar un Book por ID
     * http//:localhost:8080/api/books/1
     * @param //id
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
     * Crear un Book
     * http//:localhost:8080/api/books
     * @return Book
     */
    @PostMapping("/api/books")
    public ResponseEntity<Book> create(@RequestBody Book book, @RequestHeader HttpHeaders headers){
        System.out.println(headers.get("User-Agent"));
        System.out.println(headers.get("Content-Type"));
        // guardar el libro recibido por paramento a la db
        if(book.getId() != null){
            log.warn("trying to create a book with id");
            System.out.println("trying to create a book with id");
            return ResponseEntity.badRequest().build();
        }
        Book bookCreated = bookRepository.save(book);
        return ResponseEntity.ok(bookCreated);
    }

    /**
     * Actualizar un libro existente en la DB
     *
     */
    @PutMapping("/api/books")
    public ResponseEntity<Book> update(@RequestBody Book book){
        if(book.getId() == null){
            log.warn("Trying to update a non existent book");
            return ResponseEntity.badRequest().build();
        }
        if(!bookRepository.existsById(book.getId())){
            log.warn("Trying to update a non existent book");
            return ResponseEntity.notFound().build();
        }

        // El proceso de actualización
        Book resul = bookRepository.save(book);
        return ResponseEntity.ok(book); // El libro devuelto tiene una clave primaria
    }

    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id){
        try{
            if(!bookRepository.existsById(id)){
                log.warn("Trying to update a non existent book");
                return ResponseEntity.notFound().build();
            }
            bookRepository.deleteById(id);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return ResponseEntity.noContent().build();   // Respuesta cuando se borra un contenido con éxito
    }

    @DeleteMapping("/api/books")
    public ResponseEntity<Book> deleteAll(){
        log.info("REST Request for delete all books");
        bookRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
