package com.example.neighbourly.neighbourly.services;

import com.example.neighbourly.neighbourly.models.User;
import com.example.neighbourly.neighbourly.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean IsEmailExists(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean IsPhoneNumberExists(String phone){
        return userRepository.findByPhoneNumber(phone).isPresent();
    }

    public boolean IsEmailPhoneNumberExists(String email, String phone){
        return IsEmailExists(email) && IsPhoneNumberExists(phone);
    }

    public Optional<User> getUser(String email){
        return userRepository.findByEmail(email);
    }

    public void addUser(User user){
        userRepository.save(user);
    }

}
