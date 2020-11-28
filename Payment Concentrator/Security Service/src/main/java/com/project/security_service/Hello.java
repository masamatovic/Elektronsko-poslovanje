package com.project.security_service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {


    @RequestMapping("/")
    public String index() {
        return "Helloo!!";
    }

}