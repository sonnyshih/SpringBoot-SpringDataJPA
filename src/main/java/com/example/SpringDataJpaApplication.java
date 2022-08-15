package com.example;

import com.example.testProperties.SystemProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 1. 建議把 SpringBootApplication 放在 package最上層，就要就不需要做以下的設定
 * 2. If the Repository and the Application are not in the same package, must use the @EnableJpaRepositories
 * 3. If the Entity and the Application are not in the same package, must use the @EntityScan
*/
@SpringBootApplication
/**
 * 如果 SpringBootApplication 是放在所有的package最上層，就不需要做以下的設定
@EnableJpaRepositories(basePackages = {
        "com.example.animal",
        "com.example.fullDemo.entity"
})
@ComponentScan(basePackages = {
        "com.example.controllerTest",
        "com.example.interceptorExample",
        "com.example.animal"
})
@EntityScan(basePackages = {
        "com.example.animal"
})
*/

/**
 * 如果 SpringBootApplication 是放在所有的package最上層，就只要做以下的設定就好
 */
@ComponentScan(basePackages = {
        "com.example.*"
})
public class SpringDataJpaApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(SpringDataJpaApplication.class, args);
        SystemProperties systemProperties = ctx.getBean(SystemProperties.class);

        System.out.println(systemProperties.getName());
        System.out.println(systemProperties.getUrl());
        System.out.println(systemProperties.getPort());

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
