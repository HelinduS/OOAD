package com.example.demo.Controller;

import com.example.demo.DTO.LoginDTO;
import com.example.demo.Entity.LoginInfo;
import com.example.demo.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/log")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDTO loginDTO) {
        boolean isAuthenticated = loginService.authenticateUser(loginDTO);

        if (isAuthenticated) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful!");
            response.put("role", "USER"); // You can modify this based on your logic

            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Invalid email or password!");
            return ResponseEntity.status(401).body(response);
        }
    }



}
