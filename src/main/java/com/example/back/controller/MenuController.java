package com.example.back.controller;

import com.example.back.dto.MenuDto;
import com.example.back.jwt.JwtService;
import com.example.back.services.MenuService;
import com.example.back.services.RapportService;
import com.example.back.services.ServiceUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menu/")
public class MenuController {
    public final MenuService menuService;
    private final ServiceUser serviceUser;
    public final JwtService jwtService;
    public final RapportService rapportService;

    @PostMapping("add")
    public void createMenu(@RequestBody MenuDto menu) {
        menuService.CreateMenu(menu);

    }
    @PostMapping("addNo")
    public void createMenuNoSous(@RequestBody DtoMenuCreation menu) {
        menuService.CreateMenu(menu);

    }
    @GetMapping("getSousMenu")
    public List<String> getSousMenusByMenu(@RequestParam String name) {
        return menuService.GetSousMenuByMenu(name);
    }
    @GetMapping("")
    public List<String> getMenus(@RequestHeader String Authorization) {
       String username= jwtService.extractUsername(Authorization.substring(7));
        System.out.println(username);
        return serviceUser.getMenusByUsername(username);
    }
    @DeleteMapping("delete")
    public void deleteMenu(@RequestParam String name) {
            this.menuService.deleteMenu(name);
    }
    public record DtoMenuCreation(String name){

    }
}
