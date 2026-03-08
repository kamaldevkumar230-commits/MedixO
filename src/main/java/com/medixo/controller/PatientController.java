package com.medixo.controller;

import com.medixo.entity.Doctor;
import com.medixo.entity.Patient;
import com.medixo.entity.Report;
import com.medixo.repository.ReportRepository;
import com.medixo.service.PatientService;

import jakarta.servlet.http.HttpSession;

import com.medixo.service.DoctorService;
import com.medixo.service.OAppointmentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PatientController {

    private final PatientService patientService;
    private final DoctorService doctorService;

    public PatientController(PatientService patientService,
                             DoctorService doctorService) {

        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    // Patient Dashboard
    @Autowired
    private OAppointmentService appointmentService;
    
    @Autowired
    private ReportRepository reportRepository;
    
    @GetMapping("/patient-dashboard")
    public String patientDashboard(Model model, HttpSession session) {

        Long patientId = (Long) session.getAttribute("patientId");

        model.addAttribute("doctors", doctorService.getAllDoctors());

        model.addAttribute("doctorCount", doctorService.getAllDoctors().size());

        model.addAttribute("appointmentCount", appointmentService.countAppointments());

        // Only this patient's reports
        List<Report> reports = reportRepository.findByPatientId(patientId);

        model.addAttribute("reportCount", reports.size());

        model.addAttribute("patientId", patientId);

        return "patient-dashboard";
    }

    // Patient List Page
    @GetMapping("/patients")
    public String patientsPage(Model model) {

        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("patient", new Patient());

        return "patients";
    }

    // Save Patient
    @PostMapping("/patients/save")
    public String savePatient(@ModelAttribute Patient patient) {

        patientService.savePatient(patient);

        return "redirect:/patients";
    }
    
    
    @GetMapping("/doctor/view/{id}")
    public String viewDoctor(@PathVariable Long id, Model model) {

        Doctor doctor = doctorService.getDoctorById(id);

        model.addAttribute("doctor", doctor);

        return "doctor-view";
    }
    
    
    

}