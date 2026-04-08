package com.medixo.controller;

import com.medixo.entity.OAppointment;
import com.medixo.entity.Report;
import com.medixo.entity.User;
import com.medixo.service.OAppointmentService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DoctorAppointmentListController {

    @Autowired
    private OAppointmentService appointmentService;

    @GetMapping("/appointment-list")
    public String doctorAppointments(HttpSession session, Model model){

        User doctor = (User) session.getAttribute("user");

        Long doctorId = doctor.getId();

        model.addAttribute("list",
                appointmentService.getDoctorAppointments(doctorId));
        return "appointment-list";
    }
    
    
  /*  @GetMapping("/doctor/create-report/{appointmentId}")
    public String createReportForm(@PathVariable Long appointmentId, Model model) {

        OAppointment appointment = appointmentService.getAppointmentById(appointmentId);

        Report report = new Report();

        report.setPatientName(appointment.getPatientName());
        report.setDoctorName(appointment.getDoctorName());
        report.setAppointmentId(appointment.getId());

        model.addAttribute("report", report);
        
        
   

        return "create-report";
    }
    
    */
    @GetMapping("/doctor/create-report/{appointmentId}")
    public String createReportForm(@PathVariable Long appointmentId, Model model) {

        OAppointment appointment = appointmentService.getAppointmentById(appointmentId);

        Report report = new Report();

        // ✅ MOST IMPORTANT FIX
        report.setPatientName(appointment.getPatient().getName());
        report.setDoctorName(appointment.getDoctor().getName());

        report.setAppointmentId(appointment.getId());

        model.addAttribute("report", report);

        return "create-report";
    }
    

}