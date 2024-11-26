package com.example.demo.Service;

import com.example.demo.DTO.LoginDTO;
import com.example.demo.Entity.LoginInfo;
import com.example.demo.Repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    LoginRepository loginRepository;

    public boolean authenticateUser(LoginDTO loginDTO){
        LoginInfo loginInfo = loginRepository.findByEmailAndPassword(
                loginDTO.getEmail(), loginDTO.getPassword());

        return loginInfo != null;

    }


}
