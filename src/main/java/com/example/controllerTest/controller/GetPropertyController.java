package com.example.controllerTest.controller;

import com.example.animal.MessageEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class GetPropertyController {

    @Value("${hello}")
    private String  hello;

    @Value("${spring.datasource.username}")
    private String  username;

    @RequestMapping(path = "testProp", method = GET)
    @ResponseBody
    public MessageEntity<String> testProp(HttpSession session, SessionStatus sessionStatus) throws UnsupportedEncodingException {

        MessageEntity<String> responseEntity = new MessageEntity<>();
        responseEntity.setCode(1);
        responseEntity.setMessage("送出成功");
        responseEntity.setResult(hello + "###" + username);

        return responseEntity;
    }

}