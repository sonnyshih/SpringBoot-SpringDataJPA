package com.example.core.javascriptUtil;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JavascriptController {

    @RequestMapping("/showAlertAndDirect")
    public ModelAndView showAlert(@RequestParam(name="message") String message, @RequestParam(name="url") String url, Model model){
        model.addAttribute("message", message);
        model.addAttribute("url", url);
        return new ModelAndView("javascriptView/show_alert_redirect_view.html");
    }
}
