package com.medixo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.medixo.repository.DoctorRepository;
import com.medixo.service.DoctorService;
@Controller
public class LogoutController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        System.out.println("LOGOUT CONTROLLER HIT");

        String email = (String) session.getAttribute("userEmail");

        if (email != null) {
            System.out.println("EMAIL: " + email);
            doctorService.setDoctorOffline(email);
        } else {
            System.out.println("EMAIL NULL ❌");
        }

        session.invalidate();

        return "redirect:/";
    }
}