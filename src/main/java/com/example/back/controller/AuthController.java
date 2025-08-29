package com.example.back.controller;

import com.example.back.dto.AuthToken;
import com.example.back.services.AuthService;
import com.example.back.services.ServiceLdap;
import com.example.back.dto.AuthDto;
import com.example.back.services.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private  ServiceLdap serviceLdap;
    @Autowired
    private ServiceUser serviceUser;
    @Autowired
    private AuthService authService;
    @PostMapping("/ad_connection")
    public boolean ad_connection(@RequestBody AuthDto authDto) {
        try {


            return serviceLdap.authenticate(authDto.username(), authDto.password());
        }catch (Exception e){
            return false;
        }

    }
    @PostMapping("register")
    public boolean register(@RequestBody AuthDto authDto ) {
        authService.register(authDto);
        return true;
    }
    @PostMapping("login")
    public AuthToken login(@RequestBody AuthDto authDto) {
        try {
            if(ad_connection(authDto)) {
                return AuthToken.builder().valid(authService.login(authDto)).build();

            }
        }catch (Exception e){
            return AuthToken.builder().valid("Login Failed").build();
        }
    return AuthToken.builder().valid("Login Failed").build();
    }

    }
