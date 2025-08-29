package com.example.back.dto;

import com.example.back.entity.Filter;

import java.util.List;

public record RapportDto(String name,
                         String titre,
                         String query,
                         String host,
                         String type,
                         int port,
                         String db,
                         String username_db,
                         String password_db,
                         String menu,
                         String sousMenus,
                         List<FiltreDto> filters
                         ) {
}
