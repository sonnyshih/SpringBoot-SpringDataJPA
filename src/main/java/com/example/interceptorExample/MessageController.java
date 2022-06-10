package com.example.interceptorExample;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class MessageController {

    @GetMapping(value="/message")
    public String getMessage() {
        String message = "Hello World!!";
        System.out.println("MessageController.getMessage():" + message);
        return message;
    }

}
