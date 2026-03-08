package com.medixo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medixo.entity.OAppointment;
import com.medixo.repository.OAppointmentRepository;

@Service
public class OAppointmentService {

    @Autowired
    private OAppointmentRepository repo;

    public void saveAppointment(OAppointment appointment) {
        repo.save(appointment);
    }

    public List<OAppointment> getAllAppointments() {
        return repo.findAll();
    }
    
    
    public long countAppointments(){
        return repo.count();
    }

    public OAppointment getAppointmentById(Long id) {

        return repo.findById(id).orElse(null);

    }
    
    public List<OAppointment> getDoctorAppointments(String doctorName){

        return repo.findByDoctorName(doctorName);

    }
    
    
    public List<OAppointment> getPatientAppointments(String patientName){

        return repo.findByPatientName(patientName);

    }
}