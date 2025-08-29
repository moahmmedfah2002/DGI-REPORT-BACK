package com.example.back.controller;

import com.example.back.dto.SousMenuDto;
import com.example.back.services.SousMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sousMenu/")
public class SousMenuController {
    private final SousMenuService sousMenuService;
    @PostMapping("add")
    public void createSousMenu(@RequestBody SousMenuDto sousMenuDto) {
        sousMenuService.createSousMenu(sousMenuDto);


    }
}
