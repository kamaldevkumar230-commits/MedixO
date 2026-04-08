package com.medixo.controller;

import com.medixo.service.DoctorService;
import com.medixo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ViewController {

    private final UserService userService;

    public ViewController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about"; // 👉 about.html load karega
    }
    
    
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/")
    public String home(Model model){

        model.addAttribute("doctors", doctorService.getAllDoctors());

        return "index";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") com.medixo.entity.User user) {
        userService.saveUser(user);
        return "redirect:/";
    }
}