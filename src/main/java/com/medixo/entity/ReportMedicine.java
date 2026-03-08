package com.medixo.entity;

import jakarta.persistence.*;

@Entity
public class ReportMedicine {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String medicine;

private String dosage;

private String duration;

private Long reportId;

public Long getId() {
return id;
}

public void setId(Long id) {
this.id = id;
}

public String getMedicine() {
return medicine;
}

public void setMedicine(String medicine) {
this.medicine = medicine;
}

public String getDosage() {
return dosage;
}

public void setDosage(String dosage) {
this.dosage = dosage;
}

public String getDuration() {
return duration;
}

public void setDuration(String duration) {
this.duration = duration;
}

public Long getReportId() {
return reportId;
}

public void setReportId(Long reportId) {
this.reportId = reportId;
}

}