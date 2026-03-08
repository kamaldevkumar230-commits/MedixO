package com.medixo.controller;

import com.medixo.entity.User;
import com.medixo.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/api/users") // better practice

@Controller
public class UserController {

  /*  private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public User addUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/all")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
    
    
    */
    
    @Autowired
    private UserService service;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/doLogin")
    public String doLogin(@RequestParam String email,
                          @RequestParam String password,
                          HttpSession session,
                          Model model) {

        User user = service.login(email, password);

        if(user == null){

            model.addAttribute("error","Invalid Email or Password");
            return "login";
        }

        session.setAttribute("loggedUser", user);

        if("ADMIN".equals(user.getRole())){
            return "admin-dashboard";
        }

        if("DOCTOR".equals(user.getRole())){
            return "redirect:/doctor-dashboard";
        }

        if("PATIENT".equals(user.getRole())){
        	
        
            return "redirect:/patient-dashboard";
        }

        return "login";
    }
    
    
}