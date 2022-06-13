package com.example.controllerTest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/manager")
public class MultiPathController {

    @RequestMapping("/news")    // Url path is http://localhost:8080/manager/news
    public String showNew(){
        return "manage/news";
    }

    @RequestMapping(path = "/member", method= RequestMethod.GET)
    public String showMember(){

        return "manage/member";
    }
}
