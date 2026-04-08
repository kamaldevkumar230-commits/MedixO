package com.medixo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.medixo.entity.OAppointment;
import com.medixo.entity.User;
import com.medixo.service.OAppointmentService;

import jakarta.servlet.http.HttpSession;

@Controller
public class OAppointmentController {

    @Autowired
    private OAppointmentService service;

    // Book Appointment Page
    @GetMapping("/book")
    public String showForm(Model model) {

        model.addAttribute("appointment", new OAppointment());
        model.addAttribute("doctors", service.getAllDoctors()); // ✅ MOST IMPORTANT


        return "book-appointment";
    }


    @PostMapping("/payment-page")
    public String paymentPage(
            @RequestParam Long doctorId,
            @RequestParam String date,
            @RequestParam String time,
            @RequestParam String problem,
          
            Model model) {

        model.addAttribute("doctorId", doctorId);
        model.addAttribute("date", date);
        model.addAttribute("time", time);
        model.addAttribute("problem", problem);

       

        return "payment";
    }
    
    
    
    
    // Save Appointment
    @PostMapping("/saveAppointment")
    public String saveAppointment(@RequestParam Long doctorId,
                                 @RequestParam String date,
                                 @RequestParam String time,
                                 @RequestParam String problem,
                                 HttpSession session) {

        User patient = (User) session.getAttribute("user");

        if(patient == null){
            return "redirect:/login"; // ✅ safety
        }

        service.saveAppointment(patient.getId(), doctorId, date, time, problem);

        return "appointment-successfull";
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

        return "redirect:/appointment-list";
    }


    // Cancel Appointment
    @GetMapping("/appointment/cancel/{id}")
    public String cancelAppointment(@PathVariable Long id) {

        OAppointment a = service.getAppointmentById(id);

        a.setStatus("CANCELLED");

        service.saveAppointment(a);

        return "redirect:/appointment-list";
    }

}