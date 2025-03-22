package com.example.clone.Stomp;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {


    //  @MessageMapping 메세지가 /hello 대상에 전송되면 해당 greeting() 메서드 호출
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        //Thread.sleep(1000); // simulated delay

        // greeting 객체를 생성해서 반환함
        // 반환값은 /topic/greetings에 지정한 대로 모든 구독자에게 브로드캐스팅
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

}