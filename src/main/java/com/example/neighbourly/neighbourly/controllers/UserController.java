package com.example.neighbourly.neighbourly.controllers;

import com.example.neighbourly.neighbourly.models.User;
import com.example.neighbourly.neighbourly.security.JwtUtil;
import com.example.neighbourly.neighbourly.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public Map<String, Object> Signup(@RequestBody Map<String, Object> body){
        String name = (String)body.get("name");
        String email = (String)body.get("email");
        String password = (String)body.get("password");
        password = passwordEncoder.encode(password);
        String phone = (String)body.get("phone");
        String role = (String)body.get("role");

        if(!body.containsKey("name") ){
            return Map.of("error", (Boolean)true, "message", "name not passed in request body", "status", "400");
        }
        if(!body.containsKey("email") ){
            return Map.of("error", (Boolean)true, "message", "email not passed in request body", "status", "400");
        }
        if(!body.containsKey("password") ){
            return Map.of("error", (Boolean)true, "message", "password not passed in request body", "status", "400");
        }
        if(!body.containsKey("phone") ){
            return Map.of("error", (Boolean)true, "message", "phone not passed in request body", "status", "400");
        }
        if(!body.containsKey("role") ){
            return Map.of("error", (Boolean)true, "message", "role not passed in request body", "status", "400");
        }

        if(userService.IsEmailExists(email)){
            return Map.of("error", (Boolean)true, "message", "User with this email already exists", "status", "400");
        }
        if(userService.IsPhoneNumberExists(phone)){
            return Map.of("error", (Boolean)true, "message", "User with this phone already exists", "status", "400");
        }
        System.out.println("checking..........");

        String token = jwtUtil.GenerateToken(email, role);


        try{
            User user = new User(name, email, password, phone, role, "", "");
            userService.addUser(user);
        }
        catch (Exception e){
            return Map.of("error", (Boolean)true, "message", "Error while creating user", "status", "400");
        }

        return Map.of("error", (Boolean)false, "message", "user registered successfully", "token", token, "status", 200);
    }

    @PostMapping("/login")
    public Map<String, Object> Login(@RequestBody Map<String, Object> body){

        String email = (String)body.get("email");
        String password = (String)body.get("password");

        if(!body.containsKey("email") ){
            return Map.of("error", (Boolean)true, "message", "email not passed in request body", "status", "400");
        }

        boolean isCorrectPassword = false;
        String role;
        if(userService.getUser(email).isPresent()){
            String encodedPassword = userService.getUser(email).get().getPassword();
            isCorrectPassword = passwordEncoder.matches(password, encodedPassword);
            role = userService.getUser(email).get().getRole();
        }
        else{
            return Map.of("error", (Boolean)true, "message", "User with this email doesnt exists", "status", "400");
        }
        if(!isCorrectPassword){
            return Map.of("error", (Boolean)true, "message", "Incorrect password", "status", "400");
        }

        String token = jwtUtil.GenerateToken(email, role);

        return Map.of("error", false, "message", "Login Successful", "token", token , "status", 200);
    }


    @GetMapping("/hello")
    public String GetHello(){
        return "Hello world";
    }
}
