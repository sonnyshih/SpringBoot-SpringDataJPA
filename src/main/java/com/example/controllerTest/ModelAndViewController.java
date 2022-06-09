package com.example.controllerTest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * There are two type to display the value int the html
 * 1. Use the Map<String, Object>
 * 2. Use the Model
 * */

@Controller
public class ModelAndViewController {


    // Type 1: Use the Map<String, Object>
    @RequestMapping("/test")
    public ModelAndView testModelAndView(Map<String, Object> model){
        model.put("number", 1234);
        model.put("message", "Hello from Spring MVC");
        return new ModelAndView("test/hello");
    }


    // Type 2: Use the Model
    @RequestMapping("/test2")
    public ModelAndView testModelAndView2(Model model){
        model.addAttribute("number", 456789);
        model.addAttribute("message", "Hello from Spring MVC (Model)");
        return new ModelAndView("test/hello");
    }

    @RequestMapping("/popup")
    public ModelAndView showPopup(@RequestParam(name="message", defaultValue = "Hello from Spring MVC!!") String message, Model model){

        model.addAttribute("message", message);
//        model.addAttribute("url", "https://www.google.com");
        model.addAttribute("url", "test2"); // redirect to test2 page
        return new ModelAndView("test/popup");
    }

}
