package com.example.springmvc.controller;

import com.example.springmvc.entity.Form;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class FormController {
//    @PostMapping("/apply")
    @ResponseBody
    public String apply(@RequestParam(defaultValue = "ANON") String name, String course, Integer[] purpose){
        System.out.println(name);
        System.out.println(course);
        for (Integer p:purpose){
            System.out.println(p);
        }
        return "Success";
    }

//    @PostMapping("/apply")
    @ResponseBody
    public String apply(String name, String course, @RequestParam List<Integer> purpose){
        System.out.println(name);
        System.out.println(course);
        for (Integer p:purpose){
            System.out.println(p);
        }
        return "Success";
    }

//    @PostMapping("/apply")
    @ResponseBody
    public String apply(Form form){
        System.out.println(form.getName());
        System.out.println(form.getCourse());
        for (Integer p:form.getPurpose()){
            System.out.println(p);
        }
        return "Success";
    }

//    @PostMapping("/apply")
    @ResponseBody
    public String apply(@RequestParam Map map){
        System.out.println(map.get("name"));
        System.out.println(map.get("course"));
        System.out.println(map.get("purpose"));
        return "Success";
    }

    @PostMapping("/apply")
    @ResponseBody
    public String applyDelivery(Form form){
        System.out.println(form.getDelivery().getName());
        return "Success";
    }
}
