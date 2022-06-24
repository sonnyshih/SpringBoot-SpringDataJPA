package com.example.controllerTest.controller;

import com.example.animal.MessageEntity;
import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import org.assertj.core.api.recursive.comparison.ComparisonDifference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * refer to https://morosedog.gitlab.io/springboot-20190415-springboot27/
 *
 * */
@Controller
public class MailController {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    freemarker.template.Configuration freemarkerConfig;
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

    @RequestMapping(path = "mailHtmlTest", method = GET)
    @ResponseBody
    public MessageEntity<String> mailHtmlTest(HttpSession session, SessionStatus sessionStatus) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("jobtestshih1@gmail.com");
            helper.setTo("sonnyshih@gmail.com");
            helper.setSubject("主旨：Hello 模版信件");

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("userName", "Peter Chang");
            String templateString = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfig.getTemplate("/mail/template.html"), model);
            helper.setText(templateString,true);

//        FileSystemResource file = new FileSystemResource(new File("QrCode.png"));
//        helper.addInline("qrCode", file);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        } catch (TemplateNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (MalformedTemplateNameException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        MessageEntity<String> responseEntity = new MessageEntity<>();
        responseEntity.setCode(1);
        responseEntity.setMessage("送出成功");

        return responseEntity;
    }
}
