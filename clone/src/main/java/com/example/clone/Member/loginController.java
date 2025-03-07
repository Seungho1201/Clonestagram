package com.example.clone.Member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginController {
    @GetMapping("/login")
    String loginPage(){
        return "login.html";
    }
    @GetMapping("/login2")
    String loginPage2(){
        return "login2.html";
    }
}
