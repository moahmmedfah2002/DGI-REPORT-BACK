package com.example.back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.sql.ResultSet;
import java.util.List;

@Entity
@Data
public class RapportGen {

    @Id
    private int id;

    @ManyToOne()
    private  Rapport rapport;

    @ManyToOne()
    private User user;

    private List<String> columns;

    private List<String> content;


}
