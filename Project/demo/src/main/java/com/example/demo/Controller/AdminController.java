package com.example.demo.Controller;

import com.example.demo.DTO.AdminDTO;
import com.example.demo.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/login")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> login(@RequestBody AdminDTO adminDTO) {
        boolean isAuthenticated = adminService.authenticateAdmin(adminDTO);

        if (isAuthenticated) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Admin login successful!");
            response.put("role", "ADMIN");

            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Invalid admin email or password!");
            return ResponseEntity.status(401).body(response);
        }
    }
}

