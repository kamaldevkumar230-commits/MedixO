package com.medixo.repository;

import com.medixo.entity.Doctor;
import com.medixo.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	Doctor findByUser(User user);
	Doctor findByUserId(Long userId);
}