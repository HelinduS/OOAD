package com.example.demo.Repository;

import com.example.demo.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByEmailAndPassword(String email, String password);

    Optional<Admin> findById(Long adminId);
}
