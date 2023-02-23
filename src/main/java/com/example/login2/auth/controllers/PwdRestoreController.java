package com.example.login2.auth.controllers;

import com.example.login2.auth.entities.RequestPwdDTO;
import com.example.login2.auth.entities.RestorePwdDTO;
import com.example.login2.auth.services.PwdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/password")
public class PwdRestoreController {

    @Autowired
    private PwdService pwdService;


    @PostMapping("/request")
    public void passwordRequest (@RequestBody RequestPwdDTO requestPwdDTO ) throws Exception {
        //metto in un blocco try catch per proteggermi da eventuali attacchi: in questo modo dall'esterno
        // non posso sapere se la mail esiste o no
        try {
            pwdService.request(requestPwdDTO);
        }catch (Exception e){

        }

    }

    @PostMapping("/restore")
    public void passwordRestore (@RequestBody RestorePwdDTO restorePwdDTO) throws Exception {
        pwdService.restore(restorePwdDTO);
    }

}
