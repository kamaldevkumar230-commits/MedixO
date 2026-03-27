package com.medixo.controller;

import com.medixo.entity.User;
import com.medixo.service.UserService;
import com.medixo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repo; // 🔥 ADD THIS

    // 🔹 Register Page
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // 🔹 Save User (Registration)
  /*  @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user) {

        service.saveUser(user);

        return "redirect:/check_email";
    }
    */
    
    
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, Model model) {

        service.saveUser(user);

        model.addAttribute("email", user.getEmail());

        return "check_email"; // 👈 new page
    }
    
    
    
    
    

    // 🔥 EMAIL VERIFICATION (NEW ADD)
    @GetMapping("/verify")
    public String verifyUser(@RequestParam("token") String token) {

        User user = repo.findByVerificationToken(token);

        if (user != null) {

            user.setVerified(true);
            repo.save(user);

            // 👇 ROLE BASED PAGE RETURN
            if(user.getRole().equals("DOCTOR")) {
                return "doctor_verify_email"; // doctor wala page
            } else {
                return "verify_success"; // patient wala page
            }

        }

        return "error";
    }
}