package com.medixo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.medixo.service.AdminService;

@Controller
public class AdminController {

    @Autowired
    private AdminService service;

    @GetMapping("/admin")
    public String adminDashboard(Model model) {

        model.addAttribute("doctors", service.doctorCount());
        model.addAttribute("patients", service.patientCount());
        model.addAttribute("appointments", service.appointmentCount());

        return "admin-dashboard";
    }
}