package com.javawiz.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }

    @GetMapping("/user")
    public String getUser(){
        return "user";
    }

    @GetMapping("/admin")
    public String getHome(){
        return "admin";
    }
}
