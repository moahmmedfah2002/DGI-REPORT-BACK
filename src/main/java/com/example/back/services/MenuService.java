package com.example.back.services;

import com.example.back.controller.MenuController;
import com.example.back.dto.MenuDto;
import com.example.back.entity.Menu;
import com.example.back.entity.Profile;
import com.example.back.entity.SousMenu;
import com.example.back.reposetory.RepoMenu;
import com.example.back.reposetory.RepoProfile;
import com.example.back.reposetory.RepoSousMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final RepoMenu repoMenu;
    private final RepoSousMenu repoSousMenu;
    private final RepoProfile repoProfile;
    public List<MenuDto> getMenus() {

        List<Menu> menu =repoMenu.findAll();
        List<MenuDto> menuDtos = new ArrayList<>();
        menuDtos=menu.stream().map(e->{

            return new MenuDto(e.getId_menu(),e.getName(),new ArrayList<>());
        }).toList();
        return menuDtos;


    }
    public Menu getMenuByName(String name) {
        return repoMenu.findByName(name);
    }
    public void CreateMenu(MenuController.DtoMenuCreation dtoMenuCreation) {
        Menu menu = new Menu();
        menu.setName(dtoMenuCreation.name());
        Profile profile = repoProfile.findByName("ADMIN");

        repoMenu.save(menu);
        Menu savedMenu = repoMenu.findByName(menu.getName());
        profile.getMenus().add(savedMenu);
        repoProfile.save(profile);

    }
    public void CreateMenu(MenuDto menuDto) {
        Menu menu = new Menu();
        menu.setName(menuDto.name());
        Menu menu1=repoMenu.findByName(menu.getName());
        if(menu1==null) {
            List<SousMenu> sousMenus=repoSousMenu.findAllById(menuDto.sousMenu());
            menu.setSous_menus(sousMenus);
            repoMenu.save(menu);
        }
       else {
            List<SousMenu> sousMenus=repoSousMenu.findAllById(menuDto.sousMenu());
            List<SousMenu> sousMenus1=menu1.getSous_menus();
            for (SousMenu s : sousMenus1) {
                if(!sousMenus.contains(s)) {
                    sousMenus.add(s);
                }
            }

            repoMenu.save(menu);

        }

    }
    public List<String> GetSousMenuByMenu(String name) {
        Menu menu=this.repoMenu.findByName(name);

        return menu.getSous_menus().stream().map(SousMenu::getName).toList();

    }
    public List<String> GetMenus() {
        List<Menu> menu =repoMenu.findAll();

        return menu.stream().map(Menu::getName).toList();


    }
    public void deleteMenu(String menuName) {
        Menu menu = repoMenu.findByName(menuName);
        List<Profile> profilesWithMenu = repoProfile.findAllByMenusContaining(menu);
        for (Profile profile : profilesWithMenu) {
            profile.getMenus().remove(menu);
            repoProfile.save(profile);
        }
        menu.getSous_menus().clear();
        this.repoMenu.delete(menu);
    }


}
