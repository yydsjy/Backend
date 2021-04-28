package com.example.spring.controller;

import com.example.spring.entity.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/restful")
@CrossOrigin(origins = {"http://localhost:80"},maxAge = 3600)
public class RestfulController {
    @GetMapping("/request")
//    @ResponseBody
    public String doGetRequest(){
        return "{\"message\":\"retrieval successfully\"}";
    }

    @PostMapping("/request/{rid}")
//    @ResponseBody
    public String doPostRequest(@PathVariable("rid") Integer requestId, Person person){
        System.out.println(person.getName()+": "+person.getAge());
        return "{\"message\":\"create successfully\",\"id\":"+requestId+"}";
    }

    @PutMapping("/request")
//    @ResponseBody
    public String doPutRequest(Person person){
        System.out.println(person.getName()+": "+person.getAge());
        return "{\"message\":\"update successfully\"}";
    }

    @DeleteMapping("/request")
//    @ResponseBody
    public String doDeleteRequest(){
        return "{\"message\":\"delete successfully\"}";
    }

    @GetMapping("/person")
    public Person findByPersonId(Integer id){
        Person person = new Person();
        if(id==1){
            person.setName("mike");
            person.setAge(1);
        } else if(id==2){
            person.setName("nil");
            person.setAge(2);
        }
        return person;
    }

    @GetMapping("/persons")
    public List<Person> findPersons(){
        List<Person> list = new ArrayList<Person>();
        list.add(new Person("mike",1,new Date()));
        list.add(new Person("nil",2,new Date()));
        return list;
    }
}
