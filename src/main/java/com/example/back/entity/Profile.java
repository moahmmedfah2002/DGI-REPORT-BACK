package com.example.back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_profile;
    @Column(nullable = false,unique = true)
    private String name;
    @ManyToMany
    @JoinTable(
            name = "profile_menus",
            joinColumns = @JoinColumn(name = "profile_id_profile"),
            inverseJoinColumns = @JoinColumn(name = "menus_id_menu")
    )
    private List<Menu> menus;
}
