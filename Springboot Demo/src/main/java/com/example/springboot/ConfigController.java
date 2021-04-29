package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {
    @Autowired
    SchoolConfig schoolConfig;
    @GetMapping({"class"})
    public String gradeClass(){
        return schoolConfig.grade+" "+schoolConfig.clazz;
    }
}
