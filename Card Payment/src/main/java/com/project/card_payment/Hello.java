package com.project.card_payment;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {


    @RequestMapping("/")
    public String index() {
        return "Helloo!!";
    }

}
