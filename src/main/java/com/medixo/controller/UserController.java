package com.medixo.controller;

import com.medixo.entity.User;
import com.medixo.repository.DoctorRepository;
import com.medixo.service.DoctorService;
import com.medixo.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class UserController {


	 @Autowired 
	    private DoctorService doctorService;
    
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
    	try {

        User user = service.login(email, password);

        if(user == null){
            model.addAttribute("error","Invalid Email or Password");
            return "login";
        }

        session.setAttribute("user", user); 

        if("ADMIN".equals(user.getRole())){
            return "admin-dashboard";
        }

        if("DOCTOR".equals(user.getRole())){
        	
        	
        	if (!user.isApproved()) {
                return "redirect:/not-approved";
            }
        	
        	  session.setAttribute("userEmail", user.getEmail()); 
        	doctorService.setDoctorOnline(email);
        	
        	
            return "redirect:/doctor-dashboard";
        }

        if("PATIENT".equals(user.getRole())){

            session.setAttribute("patientName", user.getName());
            session.setAttribute("patientId", user.getId());

            return "redirect:/patient-dashboard";
        }
        
        

        } catch (RuntimeException e) {

            if (e.getMessage().equals("Wait for admin approval")) {
                return "redirect:/not-approved";
            }
        }
        
        

        return "login";
        
    }
    
    
    @GetMapping("/not-approved")
    public String notApprovedPage() {
        return "doctor_notApproved_er_page";
    }
    
}