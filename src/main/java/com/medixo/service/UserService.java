package com.medixo.service;

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

    public User login(String email,String password) {

        User user = repo.findByEmail(email);

        if(user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }
    
    
    
    public User saveUser(User user) {
        return repo.save(user);
    }
    
}