package com.medixo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medixo.repository.OAppointmentRepository;
import com.medixo.repository.PatientRepository;

@Service
public class DoctorDashboardService {

    @Autowired
    private OAppointmentRepository appointmentRepo;

    @Autowired
    private PatientRepository patientRepo;

    public long totalAppointments() {
        return appointmentRepo.count();
    }

    public long totalPatients() {
        return patientRepo.count();
    }

}