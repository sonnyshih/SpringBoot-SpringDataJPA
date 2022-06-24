package com.example.controllerTest.controller;

import com.example.animal.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class MailController {

    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping(path = "mailTest", method = GET)
    @ResponseBody
    public MessageEntity<String> mailTest(HttpSession session, SessionStatus sessionStatus) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("jobtestshih1@gmail.com");
        message.setTo("sonnyshih@gmail.com");
        message.setSubject("主旨：Hello world1111");
        message.setText("內容：這是一封測試信件，恭喜您成功發送了唷");

        mailSender.send(message);

        MessageEntity<String> responseEntity = new MessageEntity<>();
        responseEntity.setCode(1);
        responseEntity.setMessage("送出成功");

        return responseEntity;
    }

}
