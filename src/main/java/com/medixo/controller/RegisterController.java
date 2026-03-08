package com.medixo.controller;

import com.medixo.entity.User;
import com.medixo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    @Autowired
    private UserService service;

    @GetMapping("/register")
    public String registerPage(Model model) {

        model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user) {

        service.saveUser(user);

        return "redirect:/login";
    }
}