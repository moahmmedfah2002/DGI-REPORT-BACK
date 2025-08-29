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
public class SousMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id_sous_menu;
    @Column(nullable = false,unique = true)
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name= "sous_menu_rapport"
            ,joinColumns = @JoinColumn(name="id_sous_menu")
            ,inverseJoinColumns = @JoinColumn(name="id_rapport")
    )
    private List<Rapport> rapports;
}
