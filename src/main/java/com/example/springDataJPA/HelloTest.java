package com.example.springDataJPA;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class HelloTest {

    @Autowired
    private AnimalRepository animalRepository;

    @Test
    public void insert() {
//        List<Animal> list = animalRepository.findByNameContains("ti");
        System.out.println(animalRepository.findByNameContains("ca"));
        System.out.println("hello");
    }
}
