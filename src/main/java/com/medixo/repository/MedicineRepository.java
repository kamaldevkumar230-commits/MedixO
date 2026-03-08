package com.medixo.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medixo.entity.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long>{

    List<Medicine> findByCategory(String category);

}