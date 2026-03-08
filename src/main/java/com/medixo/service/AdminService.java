package com.medixo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medixo.repository.DoctorRepository;
import com.medixo.repository.PatientRepository;
import com.medixo.repository.AppointmentRepository;

@Service
public class AdminService {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private AppointmentRepository appointRepo;

    public long doctorCount() {
        return doctorRepo.count();
    }

    public long patientCount() {
        return patientRepo.count();
    }

    public long appointmentCount() {
        return appointRepo.count();
    }

}