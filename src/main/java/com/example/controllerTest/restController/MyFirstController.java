package com.example.controllerTest.restController;

import com.example.core.util.UrlUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/hello/student")
public class MyFirstController {

    @GetMapping("/abc")
    public String index(HttpServletRequest request) {

        System.out.println("path=" + UrlUtil.getCurrentWebPath(request));
        return "Hello World!";
    }


    @GetMapping("/cde")
    public String index1() {
        return "Hello World11111!";
    }

}
