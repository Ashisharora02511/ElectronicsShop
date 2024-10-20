package com.boot.electronic.store.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping( value= "test")
    public String check(){
        return "Welcome to the project";
    }
}
