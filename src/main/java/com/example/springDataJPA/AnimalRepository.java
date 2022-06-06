package com.example.springDataJPA;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnimalRepository extends CrudRepository<Animal, Long> {

    List<Animal> findByNameContains(String keyword);

}
