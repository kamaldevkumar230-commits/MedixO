package com.medixo.controller;

import com.medixo.entity.Report;
import com.medixo.entity.User;
import com.medixo.repository.ReportRepository;
import com.medixo.service.ReportService;
import com.medixo.util.PdfGenerator;

import jakarta.servlet.http.HttpSession;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReportController {

    @Autowired
    private ReportService service;

    @GetMapping("/reports")
    public String reportsPage(Model model){

        model.addAttribute("reports", service.getAllReports());

        return "reports";
    }

    @GetMapping("/add-report")
    public String addReportPage(Model model){

        model.addAttribute("report", new Report());

        return "add-report";
    }

  /*  @PostMapping("/saveReport")
    public String saveReport(@ModelAttribute Report report){

        service.saveReport(report);

        return "redirect:/reports";
    }
    
    */
    @Autowired
    private ReportRepository reportRepository;
    @PostMapping("/saveReport")
    public String saveReport(

    @ModelAttribute Report report,

    @RequestParam("medicine[]") List<String> medicines,
    @RequestParam("dosage[]") List<String> dosages,
    @RequestParam("duration[]") List<String> durations

    ) {

    report.setDate(LocalDate.now().toString());

    reportRepository.save(report);

    /* PDF generate */

    ByteArrayInputStream pdf =
    PdfGenerator.generate(report, medicines, dosages, durations);

    return "redirect:/appointment-list";

    }
    
    
    @GetMapping("/patient/reports")
    public String patientReports(Model model, HttpSession session){

    	 String patientName = (String) session.getAttribute("patientName");

        List<Report> reports = reportRepository.findByPatientName(patientName);

        model.addAttribute("reports", reports);

        return "patient-reports";
    }
    
    
    @GetMapping("/doctor/reports")
    public String doctorReports(Model model, HttpSession session){

        Long doctorId = (Long) session.getAttribute("doctorId");

        List<Report> reports = reportRepository.findByDoctorId(doctorId);

        model.addAttribute("reports", reports);

        return "doctor-reports";
    }
    
    
    
    @GetMapping("/report/delete/{id}")
    public String deleteReport(@PathVariable Long id){

        Report report = reportRepository.findById(id).orElse(null);

        if(report != null){
            reportRepository.deleteById(id);
        }

        return "redirect:/doctor/reports";
    }
    
    

}