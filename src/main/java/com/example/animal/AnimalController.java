package com.example.animal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    AnimalRepository repository;

    @RequestMapping("list")
    public String list(Model model){

        List<Animal> list = repository.findAll();

        System.out.println("list.siz="+ list.size());

        model.addAttribute("list", list);

        return "animal/animal_list";
    }
}
