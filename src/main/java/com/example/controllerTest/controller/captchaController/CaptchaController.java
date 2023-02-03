package com.example.controllerTest.controller.captchaController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

@RestController
@RequestMapping("authImage")
public class CaptchaController {

    @GetMapping("/abc")
    public String index() {
        return "Hello World!";
    }

    @GetMapping("/getCaptcha")
    public void getCode(HttpServletResponse response, HttpServletRequest request) throws Exception {
        // 获取到session
        HttpSession session = request.getSession();
        // 取到sessionid
        String id = session.getId();

        char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        // 利用图片工具生成图片
        // 返回的数组第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = CaptchaUtil.newBuilder()
                .setWidth(200)      // 預設圖片寬度
                .setHeight(60)      // 預設圖片高度
//                .setChars(chars)    // 設定字集 (如果沒設定，就會用預設的字集)
                .setSize(6)         // 預設字數
                .setLines(5)        // 預設干擾線條數
                .setFontSize(30)    // 預設字體大小
                .setTilt(true)      // 預設字體是否需要倾斜
                .setThetaDegree(20) // 預設字體斜角度
                .setBackgroundColor(Color.white) // 設定驗証碼背景顏色
                .build()
                .createImage();  //產生圖片

        // 将验证码存入Session
        session.setAttribute("SESSION_VERIFY_CODE_" + id, objs[0]);

        // 印出驗証碼
        System.out.println(objs[0]);

//        // 设置redis值的序列化方式
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        // 在redis中保存一个验证码最多尝试次数
//        // 这里采用的是先预设一个上限次数，再以reidis decrement(递减)的方式来进行验证
//        // 这样有个缺点，就是用户只申请验证码，不验证就走了的话，这里就会白白占用5分钟的空间，造成浪费了
//        // 为了避免以上的缺点，也可以采用redis的increment（自增）方法，只有用户开始在做验证的时候设置值，
//        //    超过多少次错误，就失效；避免空间浪费
//        redisTemplate.opsForValue().set(("VERIFY_CODE_" + id), "3", 5 * 60, TimeUnit.SECONDS);

        // 将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }

    @GetMapping("/checkCaptcha")
    public String checkCode(String code, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String id = session.getId();

        // 将redis中的尝试次数减一
        String verifyCodeKey = "VERIFY_CODE_" + id;
//        long num = redisTemplate.opsForValue().decrement(verifyCodeKey);

        // 如果次数次数小于0 说明验证码已经失效
//        if (num < 0) {
//            return "验证码失效!";
//        }

        // 将session中的取出对应session id生成的验证码
        String serverCode = (String) session.getAttribute("SESSION_VERIFY_CODE_" + id);
        // 校验验证码
        if (null == serverCode || null == code || !serverCode.toUpperCase().equals(code.toUpperCase())) {
            return "驗証碼錯誤!";
        }

        // 验证通过之后手动将验证码失效
//        redisTemplate.delete(verifyCodeKey);

        // 这里做具体业务相关

        return "驗証碼正確!";
    }
}
