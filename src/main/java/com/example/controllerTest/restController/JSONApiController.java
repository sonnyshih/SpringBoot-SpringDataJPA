package com.example.controllerTest.restController;

import com.example.controllerTest.controller.Account;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 *  回應與接收 JSON
 *  Refere to https://openhome.cc/Gossip/Spring/JSON.html
 */

@RestController
@RequestMapping("api")
public class JSONApiController {

    @RequestMapping(value = "showJson", method = GET)
    @ResponseBody
    public Map<String, Object> showJson(Model model, HttpServletResponse response){

        Map<String, Object> data = new HashMap<>();
        data.put("a", "10");
        data.put("b", 20);
        data.put("c", new String[] {"foo", "orz"});
        data.put("d", new Date());
        return data;
    }

    @RequestMapping(value="showJson1", method = GET)
    @ResponseBody
    public Account test2() {
        return new Account(1, "hello", "world", "good@helloworld.com");
    }

    @RequestMapping(value = "inputJson", method = POST)
    @ResponseBody
    public Account test3(@RequestBody Account account) {
        return new Account(
                account.getId(),
                account.getAccount(),
                account.getName().toUpperCase(),
                account.getEmail().toUpperCase()
        );
    }
}
