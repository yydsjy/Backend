package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParaController {
    @GetMapping("/request1")
    public String firstRequest(){
        return "Success";
    }

    @GetMapping("/request2")
    public String requestPara(@RequestParam Integer num){
        return num.toString();
    }

    @GetMapping("/request3/{num}")
    public String pathPara(@PathVariable Integer num){
        return num.toString();
    }

    @GetMapping({"/request4","/request5"})
    public String multiUrl(@RequestParam Integer num){
        return num.toString();
    }

    @GetMapping({"/request6"})
    public String required(@RequestParam(required = false,defaultValue = "0") Integer num){
        return num.toString();
    }
}
