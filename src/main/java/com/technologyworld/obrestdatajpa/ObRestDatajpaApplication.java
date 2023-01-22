package com.technologyworld.obrestdatajpa;

import com.technologyworld.obrestdatajpa.config.SwaggerConfig;
import com.technologyworld.obrestdatajpa.entities.Book;
import com.technologyworld.obrestdatajpa.repository.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication  // Me permite scanner todos los beans dentro del paquete que estamos
public class ObRestDatajpaApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(ObRestDatajpaApplication.class, args);  //Contenedor de los Beans

		BookRepository repository = context.getBean(BookRepository.class);


		/**
		 * CRUD
		 */

		/**
		 * Crea un libro
		 */
		Book book1 = new Book(null, "Homo Deus", "Yuval Noah", 450, 29.99, LocalDate.of(2018, 12, 1), true);
		Book book2 = new Book(null, "Homo Sapiens", "Yuval Noah", 450, 29.99, LocalDate.of(2018, 12, 1), true);

		repository.save(book1);
		repository.save(book2);

		System.out.println("Numero de libro en Data Base: " + repository.findAll());


		//repository.deleteById(1L);

	}

}
