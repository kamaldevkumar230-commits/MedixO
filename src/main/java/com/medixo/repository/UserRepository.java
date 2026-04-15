package com.medixo.repository;

import com.medixo.entity.Doctor;
import com.medixo.entity.User;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
	 
	 List<User> findByRoleAndIsApprovedFalse(String role);
	  List<User> findByRole(String role); // ✅ IMPORTANT
	  boolean existsByEmail(String email);
	
}