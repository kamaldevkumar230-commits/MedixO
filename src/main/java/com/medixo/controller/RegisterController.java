package com.medixo.controller;

import com.medixo.entity.Doctor;
import com.medixo.entity.User;
import com.medixo.service.EmailService;
import com.medixo.service.UserService;

import jakarta.servlet.http.HttpSession;

import com.medixo.repository.DoctorRepository;
import com.medixo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    @Autowired
    private UserService service;
    
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository repo; // 🔥 ADD THIS

    // 🔹 Register Page
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // 🔹 Save User (Registration)
  /*  @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user) {

        service.saveUser(user);

        return "redirect:/check_email";
    }
    */
    
    
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user,
                           HttpSession session,
                           Model model) {

        try {
        	
        	
        	  // 🔥 STEP 0: DUPLICATE CHECK (VERY IMPORTANT)
            if (repo.existsByEmail(user.getEmail())) {
                model.addAttribute("error", "Email already registered ❌");
                return "register";
            }
        	

            // 🔥 STEP 1: OTP generate
            String code = String.valueOf((int)(Math.random() * 900000) + 100000);

            // 🔥 STEP 2: session me store
            session.setAttribute("otp", code);
            session.setAttribute("tempUser", user); // 🔥 IMPORTANT (user ko temporarily store)

            // 🔥 STEP 3: email send
            emailService.sendVerificationCode(user.getEmail(), code);

            // 🔥 STEP 4: email show page
            model.addAttribute("email", user.getEmail());

            return "fill_email_code";

        } catch (Exception e) {

            e.printStackTrace();
            model.addAttribute("error", "Something went wrong!");

            return "register";
        }
    
    }
    
    
    
    
   /* 

    // 🔥 EMAIL VERIFICATION (NEW ADD)
    @GetMapping("/verify")
    public String verifyUser(@RequestParam("token") String token) {

        User user = repo.findByVerificationToken(token);

        if (user != null) {

            user.setVerified(true);
            repo.save(user);

            // 👇 ROLE BASED PAGE RETURN
            if(user.getRole().equals("DOCTOR")) {
                return "doctor_verify_email"; // doctor wala page
            } else {
                return "verify_success"; // patient wala page
            }

        }

        return "error";
    }
}

*/
    
    
    @Autowired
    private DoctorRepository doctorRepo;
    
    
    @GetMapping("/verify_success")
    public String patientSuccess() {
        return "verify_success";
    }

    @GetMapping("/doctor_verify_email")
    public String doctorSuccess() {
        return "doctor_verify_email";
    }

    @PostMapping("/verify-code")
    public String verifyCode(@RequestParam String code,
                             HttpSession session,
                             Model model) {

        String sessionCode = (String) session.getAttribute("otp");
        User user = (User) session.getAttribute("tempUser");

        if (sessionCode != null && sessionCode.equals(code)) {

            User savedUser = service.saveUser(user);

            // 🔥 DOCTOR LOGIC
            if ("DOCTOR".equalsIgnoreCase(savedUser.getRole())) {

                Doctor doc = doctorRepo.findByUser(savedUser);

                if (doc == null) {
                    doc = new Doctor();

                    doc.setUser(savedUser);
                    doc.setEmail(savedUser.getEmail());
                    doc.setFirstName(savedUser.getName());
                    doc.setStatus("PENDING");

                    doctorRepo.save(doc);
                }

                session.setAttribute("user", savedUser);

                return "redirect:/doctor_verify_email"; // 👈 direct
            }

            // 🔥 PATIENT LOGIC (IMPORTANT)
            return "redirect:/verify_success";

        } else {
            model.addAttribute("error", "Invalid OTP!");
            return "fill_email_code";
        }
    }
    
    
    @GetMapping("/resend-otp")
    @ResponseBody
    public String resendOtp(HttpSession session) {

        User user = (User) session.getAttribute("tempUser");

        if (user == null) {
            return "Session expired!";
        }

        // 🔥 New OTP generate
        String code = String.valueOf((int)(Math.random() * 900000) + 100000);

        // update session
        session.setAttribute("otp", code);

        // send email
        emailService.sendVerificationCode(user.getEmail(), code);

        return "OTP Sent";
    }
}