package com.example.controllerTest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyFirstController {

    @GetMapping("/abc")
    public String index() {
        return "Hello World!";
    }


    @GetMapping("/cde")
    public String index1() {
        return "Hello World11111!";
    }

}
