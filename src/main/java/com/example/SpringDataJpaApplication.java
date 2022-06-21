package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// If the Repository and the Application are not in the same package, must use the @EnableJpaRepositories
// If the Entity and the Application are not in the same package, must use the @EntityScan
@SpringBootApplication
//@EnableJpaRepositories(basePackages = {
//        "com.example.animal",
//        "com.example.fullDemo.entity"
//})
//@ComponentScan(basePackages = {
//        "com.example.controllerTest",
//        "com.example.interceptorExample",
//        "com.example.animal"
//})
//@EntityScan(basePackages = {
//        "com.example.animal"
//})
@ComponentScan(basePackages = {
        "com.example.*"
})
public class SpringDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaApplication.class, args);

        // The SQL action demo can refer to unitTest/HelloTest class
    }

//    @Bean
//    public CommandLineRunner run(AnimalRepository repository){
//        return (args -> {
////			insertJavaAdvocates(repository);
////			System.out.println(repository.findAll());
//            System.out.println("##### CommandLineRunner (Start) #####");
//            System.out.println(repository.findByNameContains("ti"));
//            System.out.println("##### CommandLineRunner (End) #####");
//        });
//
//    }
//
//    private void insertJavaAdvocates(AnimalRepository repository){
//        repository.save(new Animal("Tiger"));
//        repository.save(new Animal("Dog"));
//        repository.save(new Animal("Cat"));
//        repository.save(new Animal("Mouse"));
//    }
}
