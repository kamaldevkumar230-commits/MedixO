package com.medixo.controller;

import com.medixo.entity.Appointment;
import com.medixo.service.AppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/appointments")
    public String appointmentsPage(Model model){

        model.addAttribute("appointments", appointmentService.getAllAppointments());
        model.addAttribute("appointment", new Appointment());

        return "appointments";
    }

    @PostMapping("/appointments/save")
    public String saveAppointment(@ModelAttribute Appointment appointment){

        appointmentService.saveAppointment(appointment);

        return "redirect:/appointments";
    }

    @GetMapping("/appointments/delete/{id}")
    public String deleteAppointment(@PathVariable Long id){

        appointmentService.deleteAppointment(id);

        return "redirect:/appointments";
    }
}