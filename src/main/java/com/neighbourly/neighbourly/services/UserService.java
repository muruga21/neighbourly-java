package com.neighbourly.neighbourly.services;

import com.neighbourly.neighbourly.models.UserModel;
import com.neighbourly.neighbourly.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private  UserRepository userRepository;

    public List<UserModel> getUsers(){
        System.out.println("check");
        return userRepository.findAll();
    }

}
