package com.medixo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medixo.repository.DoctorRepository;
import com.medixo.repository.OAppointmentRepository;
import com.medixo.repository.PatientRepository;
import com.medixo.repository.UserRepository;
import com.medixo.entity.OAppointment;
import com.medixo.entity.User;
import com.medixo.repository.AppointmentRepository;

@Service
public class AdminService {

    @Autowired
    private DoctorRepository doctorRepo;
    
    @Autowired
    private OAppointmentRepository opall;
    
    @Autowired
    private UserRepository repo;

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

    
    public List<User> getPendingDoctors() {
        return repo.findByRoleAndIsApprovedFalse("DOCTOR");
    }

    public void approveDoctor(Long id) {
        User user = repo.findById(id).orElse(null);

        if(user != null) {
            user.setApproved(true);
            repo.save(user);
        }
    }
    

    // Admin ke liye
    public List<OAppointment> getAllAppointments() {
        return opall.findAll();
    }
    
}