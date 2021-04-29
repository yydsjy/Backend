package com.example.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertiesController {
    @Value("${school.grade}")
    Integer grade;
    @Value("${school.clazz}")
    Integer clazz;
    @GetMapping("/grade")
    public String gradeClass(){
        return grade+" "+clazz;
    }
}
