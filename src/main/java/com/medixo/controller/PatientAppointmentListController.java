package com.medixo.controller;

import com.medixo.entity.OAppointment;
import com.medixo.entity.Report;
import com.medixo.entity.User;
import com.medixo.repository.ReportRepository;
import com.medixo.service.OAppointmentService;
import com.medixo.util.PdfGenerator;

import jakarta.servlet.http.HttpSession;


import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PatientAppointmentListController {

    @Autowired
    private OAppointmentService appointmentService;

    @GetMapping("/pappointment-list")
    public String patientAppointments(HttpSession session, Model model){

        User patient = (User) session.getAttribute("user");

        if (patient == null) {
            return "redirect:/login";
        }

        Long patientId = patient.getId();

        List<OAppointment> list = appointmentService.getPatientAppointments(patientId);

        model.addAttribute("list", list);
        model.addAttribute("appointmentCount", list.size());

        return "patient-appointment-list";
    }
    
    @Autowired
    private ReportRepository reportRepository;
    
    @GetMapping("/report/download/{id}")
    public ResponseEntity<InputStreamResource> downloadReport(@PathVariable Long id) {

        Report report = reportRepository.findById(id).orElse(null);

        List<String> medicines = Arrays.asList(report.getMedicine().split(","));
        List<String> dosages = Arrays.asList(report.getDosage().split(","));
        List<String> durations = Arrays.asList(report.getDuration().split(","));

        ByteArrayInputStream pdf =
        PdfGenerator.generate(report, medicines, dosages, durations);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", "inline; filename=medixo_report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
    

}