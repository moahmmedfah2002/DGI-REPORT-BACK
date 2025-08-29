package com.example.back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.security.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserGenReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_user_gen_report;
    private Timestamp created_at;
    private String filter;
    private String path;
}
