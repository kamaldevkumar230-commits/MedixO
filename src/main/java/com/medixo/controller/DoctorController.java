package com.medixo.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.medixo.entity.Doctor;
import com.medixo.service.DoctorService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/doctors")
    public String doctorsPage(Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("doctor", new Doctor());
        return "doctors";
    }

  /*  @PostMapping("/doctors/save")
    public String saveDoctor(@ModelAttribute Doctor doctor) {
        doctorService.saveDoctor(doctor);
        return "redirect:/doctors";
    }
    */
    @Autowired
    private Cloudinary cloudinary;

    @PostMapping("/doctors/save")
    public String saveDoctor(@ModelAttribute Doctor doctor,
                             @RequestParam("imageFile") MultipartFile file)
            throws IOException {

        if (!file.isEmpty()) {

            Map uploadResult = cloudinary.uploader()
                    .upload(file.getBytes(), ObjectUtils.emptyMap());

            // 👇 yaha change karna hai
            String imageUrl = uploadResult.get("secure_url").toString();

            doctor.setImage(imageUrl);
        }

        doctorService.saveDoctor(doctor);

        return "redirect:/doctors";
    }
    
    @GetMapping("/doctors/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctors";
    }
    
    
    
    // Open Edit Form
    @GetMapping("/doctors/edit/{id}")
    public String editDoctor(@PathVariable Long id, Model model) {

        Doctor doctor = doctorService.getDoctorById(id);
        model.addAttribute("doctor", doctor);

        return "edit-doctor";
    }

    // Update Doctor
    @PostMapping("/doctors/update")
    public String updateDoctor(@ModelAttribute Doctor doctor) {

        doctorService.saveDoctor(doctor);
        return "redirect:/doctors";
    }
    
}