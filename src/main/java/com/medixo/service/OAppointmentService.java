package com.medixo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medixo.entity.OAppointment;
import com.medixo.entity.User;
import com.medixo.repository.AppointmentRepository;
import com.medixo.repository.OAppointmentRepository;
import com.medixo.repository.UserRepository;

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
    
   
    public List<OAppointment> getPatientAppointments(Long patientId){
        return repo.findByPatientId(patientId);
    } 
    
    
    public List<OAppointment> getDoctorAppointments(Long doctorId){
        return repo.findByDoctorId(doctorId);
    }
    
    public List<User> getAllDoctors(){
        return userRepo.findByRole("DOCTOR");
    }
    
    @Autowired
    private UserRepository userRepo;

    public void saveAppointment(Long patientId, Long doctorId,
                               String date, String time, String problem){

        User patient = userRepo.findById(patientId).get();
        User doctor = userRepo.findById(doctorId).get();

        OAppointment app = new OAppointment();

        app.setPatient(patient);
        app.setDoctor(doctor);
        app.setDate(date);
        app.setTime(time);
        app.setProblem(problem);
        app.setStatus("PENDING");

        repo.save(app);
    }
   
    
    
    
   // public long countAppointmentsByPatientName(String patientName){
      //  return repo.countByPatientName(patientName);
   // }
    
    public long countByPatientId(Long patientId){
        return repo.countByPatientId(patientId);
    }
    
    
   
}