package com.example.controllerTest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignInController {


    @GetMapping("/signInForm")
    public String signInForm() {

        return "signIn_form";
    }


    @PostMapping("/signIn")
    public String signIn(@RequestParam(name="email", required=true) String email, @RequestParam(name="password", required=true) String password, Model model) {
        model.addAttribute("email", email);
        model.addAttribute("password", password);
        return "sign_in";
    }


}
