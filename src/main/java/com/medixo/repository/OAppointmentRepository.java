package com.medixo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.medixo.entity.OAppointment;
import com.medixo.entity.User;

public interface OAppointmentRepository extends JpaRepository<OAppointment, Long> {
	
	long count();

	//List<OAppointment> findByPatientName(String patientName);

//    List<OAppointment> findByDoctorName(String doctorName);
    
   // List<OAppointment> findByStatus(String status);
    
    //long countByPatientName(String patientName);
	List<OAppointment> findByDoctorId(Long doctorId);
    
    List<OAppointment> findByPatientId(Long patientId);
    
    long countByPatientId(Long patientId);

}