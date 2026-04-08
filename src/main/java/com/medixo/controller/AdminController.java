package com.medixo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medixo.entity.OAppointment;
import com.medixo.entity.User;
import com.medixo.service.AdminService;
import com.medixo.service.AppointmentService;
import com.medixo.service.OAppointmentService;
import com.medixo.service.UserService;

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
    
    
 // 🔹 Pending doctors list
    @GetMapping("/admin/pending_doctors")
    public String pendingDoctors(Model model) {

        model.addAttribute("doctors", service.getPendingDoctors());

        return "admin_pending";
    }

    @GetMapping("/admin/approve/{id}")
    public String approveDoctor(@PathVariable Long id) {

        service.approveDoctor(id);

        return "redirect:/admin/pending_doctors";
    }
    
    @Autowired
    private UserService uservice;
    @GetMapping("/patients_list")
    public String viewPatients(Model model) {

        List<User> patients = uservice.getAllPatients();

        model.addAttribute("patients", patients);

        return "patients_list";
    }
    
    
    @Autowired
    private OAppointmentService opservice;

    @GetMapping("/appointmentsA")
    public String viewAllAppointments(Model model) {

        List<OAppointment> list = opservice.getAllAppointments();
        
        System.out.println("DATA SIZE = " + list.size()); // 👈 ADD THIS

        model.addAttribute("appointments", list);

        return "admin_appointment_list";
    }
    
    
    
}