package com.example.animal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    AnimalRepository repository;

    @RequestMapping("list")
    public String list(Model model){

        List<Animal> list = repository.findAll();

        model.addAttribute("list", list);

        return "animal/animal_list";
    }

    @RequestMapping("add")
    public String add(Model model){

        model.addAttribute("buttonName", "新增");
        model.addAttribute("action", "insert");
        return "animal/animal_add";
    }

    @RequestMapping("edit")
    public String edit(HttpServletRequest request, Model model){
        String id = request.getParameter("id");

        Animal animal = repository.findAnimalById(Integer.valueOf(id));

        model.addAttribute("buttonName", "修改");
        model.addAttribute("action", "update");
        model.addAttribute("animal", animal);

        return "animal/animal_add";
    }


    @RequestMapping("insert")
    @ResponseBody
    public MessageEntity<Boolean> insert(HttpServletRequest request, Model model){
        String name = request.getParameter("name");
        Animal animal = new Animal();
        animal.setName(name);
        repository.save(animal);

        MessageEntity<Boolean> responseEntity = new MessageEntity<>();
        responseEntity.setCode(0);
        responseEntity.setMessage("新增成功");

        return responseEntity;
    }

    @RequestMapping("update")
    @ResponseBody
    public MessageEntity<Boolean> update(HttpServletRequest request, Model model){
        String id = request.getParameter("id");
        String name = request.getParameter("name");

        Animal animal = repository.findAnimalById(Integer.valueOf(id));
        animal.setName(name);

        repository.save(animal);

        MessageEntity<Boolean> responseEntity = new MessageEntity<>();
        responseEntity.setCode(0);
        responseEntity.setMessage("修改成功");

        return responseEntity;
    }

    @RequestMapping("delete")
    @ResponseBody
    public MessageEntity<Boolean> delete(HttpServletRequest request, Model model){
        String id = request.getParameter("id");

        Animal animal = repository.findAnimalById(Integer.valueOf(id));
        repository.delete(animal);

        MessageEntity<Boolean> responseEntity = new MessageEntity<>();
        responseEntity.setCode(0);
        responseEntity.setMessage("刪除成功");

        return responseEntity;
    }

}
