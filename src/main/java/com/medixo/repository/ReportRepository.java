package com.medixo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medixo.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

	List<Report> findByPatientName(String patientName);

	    List<Report> findByDoctorId(Long doctorId);

    
    List<Report> findByAppointmentId(Long appointmentId);

}