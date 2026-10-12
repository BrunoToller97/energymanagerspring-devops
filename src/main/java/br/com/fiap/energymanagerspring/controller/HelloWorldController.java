package br.com.fiap.energymanagerspring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloWorldController {

    @GetMapping("/hello")
    public String getHelloWorld(){
        return "<h1>Hello World!</h1>";
    }

    @GetMapping("/ola")
    public String getOlaMundo(){
        return  "Olá mundo docker!";
    }
}
