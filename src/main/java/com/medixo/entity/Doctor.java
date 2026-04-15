package com.medixo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity


@Table(name = "doctors")

public class Doctor {
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	
	
	
	private User user;
	
	
	

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String doctorCode;
    private String Name;
    //private String lastName;
    private String specialization;
    private String email;
    private String phone;
    private Integer experienceYears = 0;
    private Double consultationFee = 0.00;
    private String status;
    private String image;   // 👈 Doctor Image
    
   

	private LocalDateTime createdAt;

    public Doctor() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public String getFirstName() {
		return Name;
	}

	public void setFirstName(String firstName) {
		this.Name = firstName;
	}



	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getExperienceYears() {
		return experienceYears;
	}

	public void setExperienceYears(Integer experienceYears) {
		this.experienceYears = experienceYears;
	}

	public Double getConsultationFee() {
		return consultationFee;
	}

	public void setConsultationFee(Double consultationFee) {
		this.consultationFee = consultationFee;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	 public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	@Column(name = "is_online")
	private Boolean isOnline = false;

	public Boolean getIsOnline() {
	    return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
	    this.isOnline = isOnline;
	}

    // getters & setters manually generate karo
}