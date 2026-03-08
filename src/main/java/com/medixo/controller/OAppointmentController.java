package com.medixo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.medixo.entity.OAppointment;
import com.medixo.service.OAppointmentService;

@Controller
public class OAppointmentController {

    @Autowired
    private OAppointmentService service;

    // Book Appointment Page
    @GetMapping("/book")
    public String showForm(Model model) {

        model.addAttribute("appointment", new OAppointment());

        return "book-appointment";
    }


    // Save Appointment
    @PostMapping("/saveAppointment")
    public String saveAppointment(@ModelAttribute OAppointment appointment) {

        appointment.setStatus("PENDING");

        service.saveAppointment(appointment);

        return "redirect:/oappointments";
    }


    // Show All Appointments
    @GetMapping("/oappointments")
    public String getAllAppointments(Model model) {

        model.addAttribute("list", service.getAllAppointments());

        return "appointment-list";
    }


    // Approve Appointment
    @GetMapping("/appointment/approve/{id}")
    public String approveAppointment(@PathVariable Long id) {

        OAppointment a = service.getAppointmentById(id);

        a.setStatus("APPROVED");

        service.saveAppointment(a);

        return "redirect:/oappointments";
    }


    // Cancel Appointment
    @GetMapping("/appointment/cancel/{id}")
    public String cancelAppointment(@PathVariable Long id) {

        OAppointment a = service.getAppointmentById(id);

        a.setStatus("CANCELLED");

        service.saveAppointment(a);

        return "redirect:/oappointments";
    }

}