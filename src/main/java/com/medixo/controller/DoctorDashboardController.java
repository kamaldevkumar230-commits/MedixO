package com.medixo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.medixo.repository.OAppointmentRepository;
import com.medixo.repository.PatientRepository;

@Controller
public class DoctorDashboardController {

    @Autowired
    private OAppointmentRepository appointmentRepo;

    @Autowired
    private PatientRepository patientRepo;

    @GetMapping("/doctor-dashboard")
    public String doctorDashboard(Model model){

        model.addAttribute("appointments", appointmentRepo.count());
        model.addAttribute("patients", patientRepo.count());

        return "doctor-dashboard";
    }

}