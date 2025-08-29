package com.example.back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SiExterne {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id_si_externe;
    private String host;
    private int port;
    private String db;
    private String type;
    private String username;
    private String password;
}
