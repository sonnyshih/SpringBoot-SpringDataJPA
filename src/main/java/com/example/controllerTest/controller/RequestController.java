package com.example.controllerTest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class RequestController {

    @RequestMapping(value = "testRequest", method = POST)
    public String showJson(HttpServletRequest request, Model model){

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");


        return "manage/member";
    }

}
