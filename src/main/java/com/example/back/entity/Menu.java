package com.example.back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id_menu;
    @Column(nullable = false,unique = true)
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name= "menu_rapport"
            ,joinColumns = @JoinColumn(name="id_menu")
            ,inverseJoinColumns = @JoinColumn(name="id_rapport")
    )
    private List<Rapport> rapports;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name= "profile_menu"
            ,joinColumns = @JoinColumn(name="id_menu")
            ,inverseJoinColumns = @JoinColumn(name="id_profile")
    )
    private List<Profile> profiles;
    @OneToMany(fetch = FetchType.EAGER)
    private List<SousMenu> sous_menus;

}
