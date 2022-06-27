package com.example.controllerTest.controller.mailAndFreemarkerController;

import com.example.animal.MessageEntity;
import freemarker.core.ParseException;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.*;
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
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.controllerTest.controller.mailAndFreemarkerController.Config.INSURANCE_MAP;
import static com.example.controllerTest.controller.mailAndFreemarkerController.Config.INSURANCE_MAP2;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * refer to https://morosedog.gitlab.io/springboot-20190415-springboot27/
 *
 * */
@Controller
public class MailAndFreemarkerController {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    freemarker.template.Configuration freemarkerConfig;
    @RequestMapping(path = "mailTest", method = GET)
    @ResponseBody
    public MessageEntity<String> mailTest(HttpSession session, SessionStatus sessionStatus) throws UnsupportedEncodingException {

        SimpleMailMessage message = new SimpleMailMessage();

        InternetAddress fromInternetAddress = new InternetAddress("jobtestshih1@gmail.com", "Job Test Shih");
        message.setFrom(String.valueOf(fromInternetAddress));
//        message.setFrom("jobtestshih1@gmail.com");
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

    public enum Gender {
        BOY,
        GIRL
    }

    @RequestMapping(path = "mailHtmlFun", method = GET)
    @ResponseBody
    public MessageEntity<String> mailHtmlFun(HttpSession session, SessionStatus sessionStatus) {

        System.out.println("1="+INSURANCE_MAP.get("1"));
        Member member = new Member();
        member.setName("Joe");
        member.setEmail("jobtestshih2@gmail.com");


        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("jobtestshih1@gmail.com");
            helper.setTo(member.getEmail());
            helper.setSubject("主旨：Hello 模版信件");

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("member", member);
            model.put("testMap", INSURANCE_MAP);


            /**
             * Map 的key 非 String 時，要設定 setAPIBuiltinEnabled 和 DefaultObjectWrapper (start)
             * */
            // 方法1 (key 使用字串) (「不用」設定setAPIBuiltinEnabled 和 DefaultObjectWrapper)
            Map<String, List<String>> models1 = new HashMap<>();
            models1.put(Gender.BOY.name(), Arrays.asList("小明", "中明", "大明"));
            models1.put(Gender.GIRL.name(), Arrays.asList("小红", "中红", "大红"));
            model.put("users1", models1);

            // 方法2 (key 使用enum)
            Map<Gender, List<String>> models2 = new HashMap<>();
            models2.put(Gender.BOY, Arrays.asList("小明33", "中明33", "大明33"));
            models2.put(Gender.GIRL, Arrays.asList("小红33", "中红33", "大红33"));
            model.put("users2", models2);

            // 方法3 (key 使用integer)
            Map<Integer, String> models3 = new HashMap<>();
            models3.put(1, "大卡車");
            models3.put(2, "計程車");
            models3.put(3, "小客車");
            model.put("models3", models3);

            freemarkerConfig.setAPIBuiltinEnabled(true);
            DefaultObjectWrapper defaultObjectWrapper = (DefaultObjectWrapper) freemarkerConfig.getObjectWrapper();
            defaultObjectWrapper.setUseAdaptersForContainers(true);
            /**
             * 要設定 setAPIBuiltinEnabled 和 DefaultObjectWrapper (End)
             * */


            /** ##### 可在 ftl/html 檔案中設定assign，這樣可使用java function (Start) #####
             * 說明: https://stackoverflow.com/questions/39082563/freemarker-call-a-static-util-method-from-a-template-file-ftl
             * */
            BeansWrapper wrapper = new BeansWrapper(new Version(2,3,27));
            TemplateModel statics = wrapper.getStaticModels();
            model.put("statics", statics);
            /** ##### 可在 ftl/html 檔案中設定assign，這樣可使用java function (End) ##### */


//            freemarkerConfig.setAPIBuiltinEnabled(true);
//            DefaultObjectWrapper defaultObjectWrapper = (DefaultObjectWrapper) freemarkerConfig.getObjectWrapper();
//            defaultObjectWrapper.setUseAdaptersForContainers(true);
////            freemarkerConfig.setIncompatibleImprovements(new Version("2.3.22"));
            String templateString = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfig.getTemplate("/mail/fun_template.html"), model);
            helper.setText(templateString,true);

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
