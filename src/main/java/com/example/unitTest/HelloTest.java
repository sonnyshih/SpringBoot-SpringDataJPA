package com.example.unitTest;

import com.example.springDataJPA.AnimalRepository;
import com.example.springDataJPA.SpringDataJpaApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SpringDataJpaApplication.class)
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
