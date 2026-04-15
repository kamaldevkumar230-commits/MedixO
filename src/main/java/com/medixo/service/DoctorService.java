package com.medixo.service;

import com.medixo.entity.Doctor;
import com.medixo.entity.User;
import com.medixo.repository.DoctorRepository;
import com.medixo.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor saveDoctor(Doctor doctor) {
       // doctor.setDoctorCode("DOC-" + UUID.randomUUID().toString().substring(0,5));
        doctor.setStatus("ACTIVE");
        doctor.setCreatedAt(LocalDateTime.now());
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
    
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;
    
    @Transactional
    public void approveDoctor(Long id) {

        System.out.println("APPROVE METHOD CALLED");

        Doctor doc = doctorRepository.findByUserId(id);

        if (doc != null) {

            User user = doc.getUser();

            user.setApproved(true);
            userRepository.save(user);

            // ✅ ADD THIS LINE
            doc.setStatus("ACTIVE");
            doctorRepository.save(doc);

            try {
                emailService.sendApprovalEmail(user.getEmail(), user.getName());
                System.out.println("EMAIL SENT");
            } catch (Exception e) {
                System.out.println("EMAIL ERROR: " + e.getMessage());
            }
        }
    }
    
    
    public void setDoctorOnline(String email) {

        Doctor doctor = doctorRepository.findByUserEmail(email);

        if (doctor != null) {
            doctor.setIsOnline(true);
            doctorRepository.save(doctor);
        }
    }
    
    
    
    public void setDoctorOffline(String email) {

        Doctor doctor = doctorRepository.findByUserEmail(email);

        if (doctor != null) {
            doctor.setIsOnline(false);
            doctorRepository.save(doctor);
        }
    }
    
}