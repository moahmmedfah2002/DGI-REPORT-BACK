package com.example.back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rapport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id_rapport;

    @Column(nullable = false,unique = true)
    private String name;

    private String titre;

    private String query;
    @OneToMany
    private List<Filter> filtre;

    @ManyToOne
    private SiExterne siExterne;

    private Timestamp created_at;
    @ManyToOne(fetch = FetchType.EAGER)

    private Menu menu;

    @ManyToOne(fetch = FetchType.EAGER)

    private SousMenu sousMenus;




}
