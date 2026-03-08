package com.medixo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.medixo.entity.ReportMedicine;
import java.util.List;

public interface ReportMedicineRepository extends JpaRepository<ReportMedicine,Long>{

List<ReportMedicine> findByReportId(Long reportId);

}