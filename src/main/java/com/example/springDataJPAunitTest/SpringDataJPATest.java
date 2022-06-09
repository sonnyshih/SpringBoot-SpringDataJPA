package com.example.springDataJPAunitTest;

import com.example.animal.Animal;
import com.example.animal.AnimalRepository;
import com.example.springDataJPA.SpringDataJpaApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

// If the Unit test and Application class are not in the same package, must add the classes.
// After start the Application, the unit test start to run.
@SpringBootTest(classes = SpringDataJpaApplication.class)
public class SpringDataJPATest {

    @Autowired
    private AnimalRepository animalRepository;

    @Test
    public void SpringDataJPA_Query(){
        System.out.println(animalRepository.findByNameContains("is"));
        System.out.println("hello");
    }

    @Test
    public void SpringDataJPA_Insert() {
        Animal animal = new Animal();
        animal.setName("Fish");
        animal = animalRepository.save(animal);
        System.out.println("ID="+ animal.getId());
    }

    @Test
    public void SpringDataJPA_Update() {
        Animal animal = animalRepository.findAnimalById(1);
        animal.setName("Hello Tiger");
        animalRepository.save(animal);
        System.out.println("ID="+ animal);
    }

    @Test
    public void SpringDataJPA_Delete() {
        Animal animal = animalRepository.findAnimalById(1);
        animal.setName("Hello Tiger");
        animalRepository.delete(animal);
    }

    @Test
    public void SpringDataJPA_FindAll() {
        List<Animal> list = animalRepository.findAll();
        for (Animal animal: list) {
            System.out.println("ID="+ animal.getId() + "# Name="+ animal.getName());
        }
    }



}
