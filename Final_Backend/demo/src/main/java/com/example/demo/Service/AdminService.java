package com.example.demo.Service;

import com.example.demo.DTO.AdminDTO;
import com.example.demo.Entity.Admin;
import com.example.demo.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    public boolean authenticateAdmin(AdminDTO adminDTO){
        Admin admin = adminRepository.findByEmailAndPassword(
                adminDTO.getEmail(), adminDTO.getPassword());

        return admin != null;
    }
}
