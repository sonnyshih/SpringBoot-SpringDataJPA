package com.example.unitTest;

import com.example.animal.AnimalRepository;
import com.example.springDataJPA.SpringDataJpaApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// If the Unit test and Application class are not in the same package, must add the classes.
// After start the Application, the unit test start to run.
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
