package com.example.demo.Repository;

import com.example.demo.Entity.LoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<LoginInfo, Integer> {
    LoginInfo findByEmailAndPassword(String email, String password);




}
