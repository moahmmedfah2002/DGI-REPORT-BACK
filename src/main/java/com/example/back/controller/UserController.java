package com.example.back.controller;

import com.example.back.services.ServiceUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final ServiceUser serviceUser;
    @PostMapping("addMenu")
    public void addMenu(@RequestBody DtoMenuUser dtoMenuUser) {
        serviceUser.addMenutoUser(dtoMenuUser.menus,dtoMenuUser.username);
    }
    public record DtoMenuUser(List<String> menus,String username){

    }

}
