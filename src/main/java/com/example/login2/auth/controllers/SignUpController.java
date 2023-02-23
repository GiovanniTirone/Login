package com.example.login2.auth.controllers;


import com.example.login2.auth.entities.SignUpActivationDTO;
import com.example.login2.auth.entities.SignUpDTO;
import com.example.login2.auth.services.SignUpService;
import com.example.login2.users.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @PostMapping("/signup")
    public User signup(@RequestBody SignUpDTO signUpDTO) throws Exception {
        return signUpService.signUp(signUpDTO);
    }

    @PostMapping("/signup/{role}")
    public User signup(@RequestBody SignUpDTO signUpDTO, @PathVariable String role) throws Exception {
        return signUpService.signUp(signUpDTO,role);
    }

    @PostMapping("/signup/activation")
    public User signup (@RequestBody SignUpActivationDTO signUpActivationDTO) throws Exception {
        return signUpService.activate(signUpActivationDTO);
    }

}
