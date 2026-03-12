package com.medixo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medixo.entity.Report;
import com.medixo.repository.ReportRepository;

@Service
public class ReportService {

    @Autowired
    private ReportRepository repo;

    public void saveReport(Report report){
        repo.save(report);
    }

    public List<Report> getAllReports(){
        return repo.findAll();
    }

    public List<Report> getPatientReports(String patientName){
        return repo.findByPatientName(patientName);
    }

    public List<Report> getDoctorReports(Long doctorId){
        return repo.findByDoctorId(doctorId);
    }

}