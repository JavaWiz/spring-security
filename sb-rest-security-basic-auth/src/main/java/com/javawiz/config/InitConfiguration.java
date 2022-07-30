package com.javawiz.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.javawiz.entity.Book;
import com.javawiz.repository.BookRepository;

@Configuration
public class InitConfiguration {
	@Bean
    CommandLineRunner initDatabase(BookRepository repository) {
        return args -> {
            repository.save(Book.builder().name("A Guide to the Bodhisattva Way of Life").author("Santideva").price(new BigDecimal("15.41")).build());
            repository.save(Book.builder().name("The Life-Changing Magic of Tidying Up").author("Marie Kondo").price(new BigDecimal("9.69")).build());
            repository.save(Book.builder().name("Refactoring: Improving the Design of Existing Code").author("Martin Fowler").price(new BigDecimal("47.99")).build());
        };
    }
}
