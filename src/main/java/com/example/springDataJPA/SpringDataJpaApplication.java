package com.example.springDataJPA;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaApplication.class, args);
    }


    @Bean
    public CommandLineRunner run(AnimalRepository repository){
        return (args -> {
//			insertJavaAdvocates(repository);
//			System.out.println(repository.findAll());
//            System.out.println(repository.findByNameContains("ti"));
        });

    }

    private void insertJavaAdvocates(AnimalRepository repository){
        repository.save(new Animal("Tiger"));
        repository.save(new Animal("Dog"));
        repository.save(new Animal("Cat"));
        repository.save(new Animal("Mouse"));
    }
}
