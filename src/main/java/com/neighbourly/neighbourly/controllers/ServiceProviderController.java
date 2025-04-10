package com.neighbourly.neighbourly.controllers;

import com.neighbourly.neighbourly.models.UserModel;
import com.neighbourly.neighbourly.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceProviderController {

    @Autowired
    private UserService userService ;

    @GetMapping("/getUser")
    public List<UserModel> getUsers(){
        System.out.println("check1");
        return userService.getUsers();
    }
}
