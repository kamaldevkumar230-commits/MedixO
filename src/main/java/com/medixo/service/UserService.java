package com.medixo.service;
import com.medixo.util.TokenUtil;

import com.medixo.entity.User;
import com.medixo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

   

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    
    @Autowired
    private UserRepository repo;

    public User login(String email, String password) {

        User user = repo.findByEmail(email);

        if(user != null && user.getPassword().equals(password)) {

            if(!user.isVerified()) {
                throw new RuntimeException("Please verify your email first");
            }

            if("DOCTOR".equalsIgnoreCase(user.getRole()) && !user.isApproved()) {
                throw new RuntimeException("Wait for admin approval");
            }

            return user;
        }

        return null;
    }
    
    
    @Autowired
    private EmailService emailService;
    

    public User saveUser(User user) {
    	
    	 // 🔴 Duplicate email check
        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already registered ❌");
        }

        // 🔹 Token generate
        String token = TokenUtil.generateToken();

        // 🔹 Default values set
        
        user.setVerified(true);

        // 🔹 Doctor ke liye approval false
        if("DOCTOR".equalsIgnoreCase(user.getRole())) {
            user.setApproved(false);
        } else {
            user.setApproved(true); // patient ke liye direct allow
        }

        
        // 🔹 Save user
        User savedUser = repo.save(user);
        
        System.out.println("EMAIL SEND HO RAHA HAI");

        // 🔹 Email bhejna (agar service ready hai)
      //  emailService.sendVerificationEmail(user.getEmail(), token);

        return savedUser;
    }
    
    public List<User> getAllDoctors(){
        return repo.findByRole("DOCTOR");
    }
  
    
    
}