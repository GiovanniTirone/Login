package com.example.login2.auth.controllers;

import com.example.login2.auth.entities.LoginDTO;
import com.example.login2.auth.entities.LoginRTO;
import com.example.login2.auth.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public LoginRTO login (@RequestBody LoginDTO loginDTO) throws Exception
    {
        LoginRTO loginRTO = loginService.login(loginDTO);
        if(loginRTO == null) throw new Exception("Not login"); //IMPORTANTE : non fornire informazioni su pwd e mail
        return loginRTO;
    }



}
