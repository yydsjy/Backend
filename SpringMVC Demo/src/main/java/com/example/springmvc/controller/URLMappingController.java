package com.example.springmvc.controller;

import com.example.springmvc.entity.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@RequestMapping("/r")
public class URLMappingController {
    @GetMapping("/g")
    @ResponseBody
    public String getMapping(@RequestParam("manager_name") String managerName,Date createTime){
        System.out.println(managerName+" "+createTime);
        return "<h1>Get</h1>";
    }
    @PostMapping("/p")
    @ResponseBody
    public String postMapping(String username, String password, @DateTimeFormat(pattern = "yyyy-MM-dd") Date createTime){
        System.out.println(username+": "+password+" "+createTime);
        return "Post";
    }
    @PostMapping("/pu")
    @ResponseBody
    public String postMappingUser(User user){
        System.out.println(user.getUsername()+": "+user.getPassword()+" "+user.getCreateTime());
        return "Post User";
    }
//    @GetMapping("/view")
    public ModelAndView showView(Integer userId){
//        ModelAndView mav = new ModelAndView("/view.jsp");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/view.jsp");
        User user = new User();
        if (userId==1){
            user.setUsername("mike");
        } else if (userId==2){
            user.setUsername("nil");
        }
        mav.addObject("u",user);
        return mav;
    }
    @GetMapping("/view")
    public String showView2(Integer userId, ModelMap modelMap){
        String view = "/view.jsp";
        User user = new User();
        if (userId==1){
            user.setUsername("mike");
        } else if (userId==2){
            user.setUsername("nil");
        }
        modelMap.addAttribute("u",user);
        return view;
    }
}
